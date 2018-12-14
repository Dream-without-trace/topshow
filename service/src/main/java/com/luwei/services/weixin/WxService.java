package com.luwei.services.weixin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luwei.common.constants.RoleConstant;
import com.luwei.common.enums.type.AgeType;
import com.luwei.common.enums.type.BillType;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.enums.type.SexType;
import com.luwei.common.property.AppProperties;
import com.luwei.common.utils.RandomUtil;
import com.luwei.models.area.Area;
import com.luwei.models.integralset.IntegralSet;
import com.luwei.models.integralset.IntegralSetDao;
import com.luwei.models.user.User;
import com.luwei.models.user.receiving.ReceivingAddress;
import com.luwei.module.shiro.service.ShiroTokenService;
import com.luwei.services.area.AreaService;
import com.luwei.services.integral.bill.IntegralBillService;
import com.luwei.services.integral.bill.web.IntegralBillDTO;
import com.luwei.services.user.UserService;
import com.luwei.services.user.address.UserReceivingAddressService;
import com.luwei.services.user.web.UserWebVO;
import com.luwei.services.weixin.pojos.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;
import java.util.List;

/**
 * @author Leone
 * @since 2018-07-31
 **/
@Slf4j
@Service
public class WxService {

    private static final Base64.Encoder encoder = Base64.getEncoder();

    private static final Base64.Decoder decoder = Base64.getDecoder();

    @Resource
    private ShiroTokenService shiroTokenService;

    @Resource
    private AppProperties appProperties;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private UserService userService;

    @Resource
    private AreaService areaService;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private IntegralBillService integralBillService;

    @Resource
    private UserReceivingAddressService userReceivingAddressService;
    @Resource
    private IntegralSetDao integralSetDao;

    /**
     * 获取access_token
     *
     * @param cede
     * @return TokenVO
     */
    public TokenVO getToken(String cede) {
        String token_url = appProperties.getWx().getTokenUrl();
        token_url = String.format(token_url, appProperties.getWx().getApp_id(), appProperties.getWx().getApp_secret());
        log.info(token_url);
        return restTemplate.getForObject(token_url, TokenVO.class);
    }


    /**
     * 通过code获取openid和session_key
     *
     * @param code
     */
    public OpenidVO openid(String code) {
        String openIdUrl = appProperties.getWx().getSessionKeyUrl();
        openIdUrl = String.format(openIdUrl, appProperties.getWx().getApp_id(), appProperties.getWx().getApp_secret(), code);
        String openidResponse = restTemplate.getForObject(openIdUrl, String.class);
        OpenidVO openidVO = null;
        try {
            openidVO = objectMapper.readValue(openidResponse, OpenidVO.class);
            log.info("openidVO:{}", openidVO);
        } catch (Exception e) {
            log.info("{}", e.getMessage());
        }
        String token;
        if (null != openidVO.getOpenid()) {
            User user = userService.findByOpenidNew(openidVO.getOpenid());
            if (null != user) {
                token = shiroTokenService.login(user.getUserId().toString(), RoleConstant.USER);
            } else {
                token = shiroTokenService.login("9" + RandomUtil.getNum(5), RoleConstant.VISITOR);
            }
            openidVO.setToken(token);
        }
        return openidVO;
    }

    /**
     * 用户登录
     *
     * @param wxUser
     */
    public UserWebVO login(WxUserDTO wxUser) {
        User user = userService.findByOpenidNew(wxUser.getOpenid());
        if (null == user) {
            log.warn("创建了用户:{}", wxUser);
            user = new User();
            BeanUtils.copyProperties(wxUser, user);
            if (wxUser.getGender().equals("1")) {
                user.setSex(SexType.MAN);
            } else if (wxUser.getGender().equals("0")) {
                user.setSex(SexType.WOMAN);
            } else {
                user.setSex(SexType.UNKNOWN);
            }
            Area city = areaService.findByPinYin(wxUser.getCity());
            Area province = areaService.findByPinYin(wxUser.getProvince());
            if (!ObjectUtils.isEmpty(city)) {
                user.setCity(city.getName());
            }
            if ((!ObjectUtils.isEmpty(province))) {
                user.setProvince(province.getName());
            }

            user.setOpenid(wxUser.getOpenid());
            user.setUsername("");
            user.setFirst(false);
            user.setIntegral(0);
            user.setMicroBlog("");
            user.setPhone("");
            user.setDisable(FlagType.DENY);
            user.setAge(AgeType.UNKNOWN);
            List<IntegralSet> integralSets = integralSetDao.findAll();
            if (integralSets != null && integralSets.size() > 0){
                IntegralSet integralSet = integralSets.get(0);
                if (integralSet != null) {
                    Integer firstLoginIntegral = integralSet.getFirstLoginIntegral();
                    if (firstLoginIntegral != null) {
                        user.setIntegral(firstLoginIntegral);
                    }
                }
            }
            userService.saveUser(user);
            if (user.getIntegral() != null && user.getIntegral() != 0) {
                // 保存用户积分流水
                integralBillService.save(new IntegralBillDTO(user.getUserId(), user.getNickname(), user.getPhone(),
                        user.getIntegral(),user.getIntegral(), BillType.INCOME, "首次登陆赠送积分"));
            }
        }
        ReceivingAddress receivingAddress = userReceivingAddressService.findByUserIdNew(user.getUserId());
        UserWebVO userWebVO = new UserWebVO();
        BeanUtils.copyProperties(user, userWebVO);

        if (!user.getFirst()) {
            userWebVO.setIsCompleteInfo(FlagType.DENY);
        } else {
            userWebVO.setIsCompleteInfo(FlagType.RIGHT);
        }

        if (StringUtils.isEmpty(user.getPhone())) {
            userWebVO.setIsBingingPhone(FlagType.DENY);
        } else {
            userWebVO.setIsBingingPhone(FlagType.RIGHT);
        }

        if (ObjectUtils.isEmpty(receivingAddress)) {
            userWebVO.setIsAddress(FlagType.DENY);
        } else {
            userWebVO.setIsAddress(FlagType.RIGHT);
        }

        return userWebVO;
    }

    /**
     * 绑定手机号
     *
     * @param dto
     */
    public void bingingPhone(PhoneNumDTO dto) throws Exception {
        log.info("{}", dto);
        byte[] dataByte = decoder.decode(dto.getEncryptedData());
        byte[] keyByte = decoder.decode(dto.getSessionKey());
        byte[] ivByte = decoder.decode(dto.getIv());
        String result = decrypt(keyByte, ivByte, dataByte);
        PhoneEncrypted phoneInfo = objectMapper.readValue(result, PhoneEncrypted.class);
        User user = userService.findOne(dto.getUserId());

        user.setPhone(phoneInfo.getPhoneNumber());
        userService.save(user);
    }

    /**
     * 解析微信手机号码数据
     *
     * @param key
     * @param iv
     * @param encData
     * @return
     * @throws Exception
     */
    public static String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        return new String(cipher.doFinal(encData), StandardCharsets.UTF_8);
    }
}
