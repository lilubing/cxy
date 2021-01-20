package com.llb.cxy.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.llb.cxy.core.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author liuyadu
 */
@Slf4j
public class SignatureUtils {
    /**
     * 5分钟有效期
     */
    private final static long MAX_EXPIRE = 5 * 60;

    public static void main(String[] args) {
        String clientSecret = "0osTIhce7uPvDKHz6aa67bhCukaKoYl4";
        //参数签名算法测试例子
        HashMap<String, String> signMap = new HashMap<>(0);
        signMap.put("AppId", "1552274783265");
        signMap.put("SignType", SignType.SHA256.name());
        signMap.put("Timestamp", DateUtil.getYearSecondToString());
        signMap.put("Nonce", RandomValueUtils.randomAlphanumeric(16));
        String sign = getSign(signMap, clientSecret);
        System.out.println("签名结果:" + sign);
        signMap.put("Sign", sign);
        System.out.println("签名参数:" + JSONObject.toJSONString(signMap));
        System.out.println(validateSign(signMap, clientSecret));
    }

    /**
     * 验证参数
     *
     * @param paramsMap
     */
    public static void validateParams(Map<String, String> paramsMap) {
        Assert.notNull(paramsMap.get(CommonConstants.APP_ID_KEY), String.format("签名验证失败:%s不能为空", CommonConstants.APP_ID_KEY));
        Assert.notNull(paramsMap.get(CommonConstants.NONCE_KEY), String.format("签名验证失败:%s不能为空", CommonConstants.NONCE_KEY));
        Assert.notNull(paramsMap.get(CommonConstants.TIMESTAMP_KEY), String.format("签名验证失败:%s不能为空", CommonConstants.TIMESTAMP_KEY));
        Assert.notNull(paramsMap.get(CommonConstants.SIGN_TYPE_KEY), String.format("签名验证失败:%s不能为空", CommonConstants.SIGN_TYPE_KEY));
        Assert.notNull(paramsMap.get(CommonConstants.SIGN_KEY), String.format("签名验证失败:%s不能为空", CommonConstants.SIGN_KEY));
        if (!SignType.contains(paramsMap.get(CommonConstants.SIGN_TYPE_KEY))) {
            throw new IllegalArgumentException(String.format("签名验证失败:%s必须为:%s,%s", CommonConstants.SIGN_TYPE_KEY, SignType.MD5, SignType.SHA256));
        }
        try {
            DateUtil.parseDate(paramsMap.get(CommonConstants.TIMESTAMP_KEY), "yyyyMMddHHmmss");
        } catch (ParseException e) {
            throw new IllegalArgumentException(String.format("签名验证失败:%s格式必须为:%s", CommonConstants.TIMESTAMP_KEY, "yyyyMMddHHmmss"));
        }
        String timestamp = paramsMap.get(CommonConstants.TIMESTAMP_KEY);
        Long clientTimestamp = Long.parseLong(timestamp);
        //判断时间戳 timestamp=201808091113
        if ((DateUtil.getCurrentTimestamp() - clientTimestamp) > MAX_EXPIRE) {
            throw new IllegalArgumentException(String.format("签名验证失败:%s已过期", CommonConstants.TIMESTAMP_KEY));
        }
    }

    /**
     * @param paramsMap    必须包含
     * @param clientSecret
     * @return
     */
    public static boolean validateSign(Map<String, String> paramsMap, String clientSecret) {
        try {
            validateParams(paramsMap);
            String sign = paramsMap.get(CommonConstants.SIGN_KEY);
            //重新生成签名
            String signNew = getSign(paramsMap, clientSecret);
            //判断当前签名是否正确
            if (signNew.equals(sign)) {
                return true;
            }
        } catch (Exception e) {
            log.error("validateSign error:{}", e.getMessage());
            return false;
        }
        return false;
    }


    /**
     * 得到签名
     *
     * @param paramMap     参数集合不含clientSecret
     *                     必须包含clientId=客户端ID
     *                     signType = SHA256|MD5 签名方式
     *                     timestamp=时间戳
     *                     nonce=随机字符串
     * @param clientSecret 验证接口的clientSecret
     * @return
     */
    public static String getSign(Map<String, String> paramMap, String clientSecret) {
        if (paramMap == null) {
            return "";
        }
        //排序
        Set<String> keySet = paramMap.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        String signType = paramMap.get(CommonConstants.SIGN_TYPE_KEY);
        SignType type = null;
        if (MyStringUtils.isNotBlank(signType)) {
            type = SignType.valueOf(signType);
        }
        if (type == null) {
            type = SignType.MD5;
        }
        for (String k : keyArray) {
            if (k.equals(CommonConstants.SIGN_KEY) || k.equals(CommonConstants.SECRET_KEY)) {
                continue;
            }
            if (paramMap.get(k).trim().length() > 0) {
                // 参数值为空，则不参与签名
                sb.append(k).append("=").append(paramMap.get(k).trim()).append("&");
            }
        }
        //暂时不需要个人认证
        sb.append(CommonConstants.SECRET_KEY + "=").append(clientSecret);
        String signStr = "";
        //加密
        switch (type) {
            case MD5:
                signStr = EncryptUtil.md5Hex(sb.toString()).toLowerCase();
                break;
            case SHA256:
                signStr = EncryptUtil.sha256Hex(sb.toString()).toLowerCase();
                break;
            default:
                break;
        }
        return signStr;
    }


    public enum SignType {
        MD5,
        SHA256;

        public static boolean contains(String type) {
            for (SignType typeEnum : SignType.values()) {
                if (typeEnum.name().equals(type)) {
                    return true;
                }
            }
            return false;
        }
    }
}
