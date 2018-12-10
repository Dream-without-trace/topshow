package com.luwei.module.qiniu;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *
 * @author Leone
 **/
@Service
public class QiNiuService {

    private final Logger logger = LoggerFactory.getLogger(QiNiuService.class);

    private Configuration configuration;

    private UploadManager uploadManager;

    private Auth auth;

    @Resource
    private QiNiuProperties properties;

    @PostConstruct
    public void init() {
        configuration = new Configuration(Zone.zone0());
        uploadManager = new UploadManager(configuration);
        auth = Auth.create(properties.getAccessKey(), properties.getSecretKey());
    }

    /**
     * 获取token
     *
     * @return
     */
    public String getToken() {
        return auth.uploadToken(properties.getBucket());
    }

    /**
     * 获取token
     *
     * @param bucket
     * @return
     */
    public QiNiuToken getToken(String bucket) {
        String token = auth.uploadToken(properties.getBucket(), bucket);
        return new QiNiuToken(token, properties.getLinkAddress());
    }

    /**
     * 上传单个文件
     *
     * @param file
     * @return
     */
    public FileVO upload(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            String token = getToken();
            Response res = uploadManager.put(fileBytes, null, token);
            QiNiu qiniu = res.jsonToObject(QiNiu.class);
            return new FileVO(Collections.singletonList(properties.getLinkAddress() + qiniu.getKey()));
        } catch (IOException e) {
            logger.error("message:{}", e.getMessage());
            return null;
        }
    }

    /**
     * 批量上传文件
     *
     * @param files
     * @return
     */
    public FileVO uploadBatch(MultipartFile[] files) {
        if (null == files || files.length < 1) {
            throw new RuntimeException("file array is empty");
        }
        List<String> list = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            try {
                byte[] fileBytes = files[i].getBytes();
                Response res = uploadManager.put(fileBytes, null, getToken());
                QiNiu qiniu = res.jsonToObject(QiNiu.class);
                list.add(properties.getLinkAddress() + qiniu.getKey());
            } catch (IOException e) {
                logger.error("message:{}", e);
            }
        }
        return new FileVO(list);
    }


    /**
     * 上传流文件
     *
     * @param inputStream
     * @return
     */
    public FileVO uploadStream(InputStream inputStream) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            byte[] byteData = outputStream.toByteArray();
            Response res = uploadManager.put(byteData, null, getToken());
            QiNiu qiniu = res.jsonToObject(QiNiu.class);
            return new FileVO(Collections.singletonList(properties.getLinkAddress() + qiniu.getKey()));
        } catch (IOException e) {
            logger.error("message:{}", e);
            return null;
        }
    }
    /** 5. 获取response header中Content-Disposition中的filename值
     * @desc ：
     *
     * @param response  响应
     * @return String
     */
    public static String getFileName(HttpResponse response) {
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        String filename = null;
        if (contentHeader != null) {
            HeaderElement[] values = contentHeader.getElements();
            if (values.length == 1) {
                NameValuePair param = values[0].getParameterByName("filename");
                if (param != null) {
                    try {
                        //filename = new String(param.getValue().toString().getBytes(), "utf-8");
                        //filename=URLDecoder.decode(param.getValue(),"utf-8");
                        filename = param.getValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return filename;
    }


    public FileVO uploadStreamByUrl(String purl) {
        HttpGet httpGet = new HttpGet(purl);
        //2.配置请求属性
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
        httpGet.setConfig(requestConfig);

        //3.发起请求，获取响应信息
        //3.1 创建httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        FileVO fileVo = null;
        try {
            //5. 发起请求，获取响应信息
            response = httpClient.execute(httpGet, new BasicHttpContext());
            System.out.println("HttpStatus.SC_OK:"+ HttpStatus.SC_OK);
            System.out.println("response.getStatusLine().getStatusCode():"+response.getStatusLine().getStatusCode());
            System.out.println("http-header:"+ JSON.toJSONString( response.getAllHeaders() ));
            System.out.println("http-filename:"+getFileName(response) );
            //请求成功
            if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
                //6.取得请求内容
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    //这里可以得到文件的类型 如image/jpg /zip /tiff 等等 但是发现并不是十分有效，有时明明后缀是.rar但是取到的是null，这点特别说明
                    System.out.println(entity.getContentType());
                    //可以判断是否是文件数据流
                    System.out.println(entity.isStreaming());


                    //6.2 输入流：从钉钉服务器返回的文件流，得到网络资源并写入文件
                    InputStream inputStream = entity.getContent();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                    inputStream.close();
                    byte[] byteData = outputStream.toByteArray();
                    Response res = uploadManager.put(byteData, null, getToken());
                    QiNiu qiniu = res.jsonToObject(QiNiu.class);
                    fileVo = new FileVO(Collections.singletonList(properties.getLinkAddress() + qiniu.getKey()));
                }
                if (entity != null) {
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            System.out.println("request url=" + purl + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) try {
                response.close();                       //释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileVo;
    }
}
