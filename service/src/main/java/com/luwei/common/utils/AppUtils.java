package com.luwei.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luwei.services.pay.pojos.NotifyDTO;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>应用工具集合
 *
 * @author Leone
 **/
@Slf4j
public class AppUtils {

    private AppUtils() {
    }

    private static final ObjectMapper objectmapper = new ObjectMapper();


    /**
     * 校验手机号
     *
     * @param phone
     * @return
     */
    public static boolean isMobile(String phone) {
        Pattern pattern = Pattern.compile("^[1][3,4,5,7,8,9][0-9]{9}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 生成md5摘要
     *
     * @param content
     * @return
     */
    public static String MD5(String content) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(content.getBytes(StandardCharsets.UTF_8));
            byte[] hashCode = messageDigest.digest();
            return new HexBinaryAdapter().marshal(hashCode).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * XML格式字符串转换为Map
     *
     * @param xmlStr
     * @return
     */
    public static Map<String, String> xmlToMap(String xmlStr) {
        try (InputStream inputStream = new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8))) {
            Map<String, String> data = new HashMap<>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            return data;
        } catch (Exception ex) {
            log.warn("xml convert to map failed message: {}", ex.getMessage());
            return null;
        }
    }


    /**
     * 支付参数生成签名
     *
     * @param params
     * @param apiKey
     * @return
     */
    public static String createSign(Map<String, String> params, String apiKey) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> set = params.entrySet();
        for (Map.Entry<String, String> entry : set) {
            String k = entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append("key=").append(apiKey);
        return MD5(sb.toString()).toUpperCase();
    }

    /**
     * 支付参数生成签名
     *
     * @param params
     * @return
     */
    public static String createSign(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> set = params.entrySet();
        for (Map.Entry<String, String> entry : set) {
            String k = entry.getKey();
            Object v = entry.getValue();
        }
        return MD5(sb.toString()).toUpperCase();
    }

    /**
     * map转换为xml格式
     *
     * @param params
     * @return
     */
    public static String mapToXml(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> es = params.entrySet();
        Iterator<Map.Entry<String, String>> it = es.iterator();
        sb.append("<xml>");
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String k = entry.getKey();
            Object v = entry.getValue();
            sb.append("<").append(k).append(">").append(v).append("</").append(k).append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 匹配ip是否合法
     *
     * @param ip
     * @return
     */
    public static Boolean isIp(String ip) {
        String re = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    /**
     * 获取ip
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (Objects.nonNull(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (Objects.nonNull(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 过滤掉关键参数
     *
     * @param param
     * @return
     */
    public static HashMap<String, String> paramFilter(Map<String, String> param) {
        HashMap<String, String> result = new HashMap<>();
        if (param == null || param.size() <= 0) {
            return result;
        }
        for (String key : param.keySet()) {
            String value = param.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 把Request中的数据解析为xml
     *
     * @param request
     * @return
     */
    public static String requestDataToXml(HttpServletRequest request) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * xml 转换 bean
     *
     * @param clazz
     * @param xml
     * @param <T>
     * @return
     */
    public static <T> T xmlToBean(Class<T> clazz, String xml) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
//        Map<String, String> resultMap = new HashMap<>();
//        resultMap.put("appId", "wxab48bb705b1cf067");
//        resultMap.put("nonceStr", RandomUtil.getNum(12));
//        resultMap.put("package", "prepay_id=wx06155800330768dcea8e7fa23627719445");
//        resultMap.put("paySign", "BFAD32640DAB566AA913C3DC5EC603AD");
//        resultMap.put("signType", "MD5");
//        resultMap.put("timeStamp", RandomUtil.getDateStr(14));


        String wx = "<xml>" +
                "<appid><![CDATA[wx2421b1c4370ec43b]]></appid>" +
                "<attach><![CDATA[支付测试]]></attach>" +
                "<bank_type><![CDATA[CFT]]></bank_type>" +
                "<fee_type><![CDATA[CNY]]></fee_type>" +
                "<is_subscribe><![CDATA[Y]]></is_subscribe>" +
                "<mch_id><![CDATA[10000100]]></mch_id>" +
                "<nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>" +
                "<openid><![CDATA[ow1r64oog831axV4MwFdwKJOdi0c]]></openid>" +
                "<out_trade_no><![CDATA[A24455678995332]]></out_trade_no>" +
                "<result_code><![CDATA[SUCCESS]]></result_code>" +
                "<return_code><![CDATA[SUCCESS]]></return_code>" +
                "<sub_mch_id><![CDATA[10000100]]></sub_mch_id>" +
                "<time_end><![CDATA[20140903131540]]></time_end>" +
                "<total_fee>1</total_fee>" +
                "<trade_type><![CDATA[JSAPI]]></trade_type>" +
                "<transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>" +
                "</xml>";

        Map<String, String> resultMap = xmlToMap(wx);
        System.out.println(createSign(resultMap, "EA64C3AE4334864F26EF49C416F8AF1D"));
        NotifyDTO result = xmlToBean(NotifyDTO.class, wx);
        System.out.println(result);
//        a7973590bdb44b2e81edbc442c9d6b75
        System.out.println(UUID.randomUUID().toString().replace("-", ""));

    }

}
