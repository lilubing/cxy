package com.llb.cxy.core.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.zip.CRC32;

/**
 * description: EncryptUtil <br>
 * date: 2019/12/9 14:27 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public class EncryptUtil extends DigestUtils {

    private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    /**
     * 计算大文件 md5获取getMD5(); SHA1获取getSha1() CRC32获取 getCRC32()
     */
    private static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f'};

    public static String encodeBase64(byte[] bytes){
        String encoded = Base64.getEncoder().encodeToString(bytes);
        return encoded;
    }

    public static byte[]  decodeBase64(String str){
        byte[] bytes = null;
        bytes = Base64.getDecoder().decode(str);
        return bytes;
    }

    public static String encodeUTF8StringBase64(String str){
        String encoded = null;
        try {
            encoded = Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.warn("不支持的编码格式",e);
        }
        return encoded;

    }

    public static String  decodeUTF8StringBase64(String str){
        String decoded = null;
        byte[] bytes = Base64.getDecoder().decode(str);
        try {
            decoded = new String(bytes,"utf-8");
        }catch(UnsupportedEncodingException e){
            logger.warn("不支持的编码格式",e);
        }
        return decoded;
    }

    public static String encodeURL(String url) {
        String encoded = null;
        try {
            encoded =  URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.warn("URLEncode失败", e);
        }
        return encoded;
    }


    public static String decodeURL(String url) {
        String decoded = null;
        try {
            decoded = URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.warn("URLDecode失败", e);
        }
        return decoded;
    }

    /**
     * AES加密(可逆)
     *
     * @param plainText  明文
     * @param privateKey 密钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encryptAES(String plainText, String privateKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");

            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(privateKey.getBytes());
            kgen.init(128, random);

            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = plainText.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] byteRresult = cipher.doFinal(byteContent);
            String sb = new String("");

            for (int i = 0; i < byteRresult.length; i++) {
                String hex = Integer.toHexString(byteRresult[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb = sb.concat(hex.toUpperCase());
            }
            return sb;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * AES解密
     *
     * @param cipherText 密文
     * @param privateKey 密钥
     * @return
     * @throws Exception
     */
    public static String decryptAES(String cipherText, String privateKey) {
        try {
            if (cipherText.length() < 1) {
                return null;
            }
            byte[] byteRresult = new byte[cipherText.length() / 2];
            for (int i = 0; i < cipherText.length() / 2; i++) {
                int high = Integer.parseInt(cipherText.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(cipherText.substring(i * 2 + 1, i * 2 + 2), 16);
                byteRresult[i] = (byte) (high * 16 + low);
            }
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(privateKey.getBytes());
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] result = cipher.doFinal(byteRresult);
            return new String(result);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 加密DES(可逆)
     *
     * @param plainText  明文
     * @param privateKey 密钥
     * @return
     */
    public static String encryptDES(String plainText, String privateKey) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("DES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(privateKey.getBytes());

            keygen.init(56, secureRandom);
            SecretKey secretKey = keygen.generateKey();

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherBytes = cipher.doFinal(plainText.getBytes("utf-8"));
            byte[] plainTextBytes = Base64.getEncoder().encode(cipherBytes);

            return new String(plainTextBytes, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密DES
     *
     * @param cipherText 密文
     * @param privateKey 密钥
     * @return
     */
    public static String decryptDES(String cipherText, String privateKey) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("DES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(privateKey.getBytes());

            keygen.init(56, secureRandom);
            SecretKey secretKey = keygen.generateKey();

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText.getBytes("utf-8"));
            byte[] cipherBytes = cipher.doFinal(cipherTextBytes);

            return new String(cipherBytes, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取文件md5值
     *
     * @return md5串
     */
    public static String md5(File file) {
        try {
            //encrypt
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            FileInputStream in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            messagedigest.update(byteBuffer);
            return bufferToHex(messagedigest.digest());
        } catch (Exception e) {
            return null;
        }

    }

    /***
     * 获取文件SHA1值
     *
     * @return String 适用于上G大的文件
     */
    public static String sha1(File file) {
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");
            FileInputStream in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            messagedigest.update(byteBuffer);
            return bufferToHex(messagedigest.digest());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取文件SHA256值
     *
     * @return String
     */
    public static String sha256(File file) {
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("SHA-256");
            FileInputStream in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            messagedigest.update(byteBuffer);
            return bufferToHex(messagedigest.digest());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取文件CRC32码
     *
     * @return String
     */
    public static String crc32(File file) {
        CRC32 crc32 = new CRC32();
        // MessageDigest.get
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                crc32.update(buffer, 0, length);
            }
            return crc32.getValue() + "";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 计算二进制数据
     *
     * @return
     */
    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static void main(String [] args){

        String en = encryptDES("hahahaha", "yaer");
        String de = decryptDES("kzWPLLyAsDeBr84lL2COsA==", "yaer");
        System.out.println(de);
        System.out.println(en);

        en = encryptAES("hahahaha", "yaer");
        de = decryptAES("FBC82B89BAA1FBBDF3AE086A09D57E7C", "yaer");
        System.out.println(de);
        System.out.println(en);
        /*
        String str = "abcd{'a':'b'}";
        String encoded = EncryptUtil.encodeUTF8StringBase64(str);
        String decoded = EncryptUtil.decodeUTF8StringBase64(encoded);
        System.out.println(str);
        System.out.println(encoded);
        System.out.println(decoded);

        String url = "== wo";
        String urlEncoded = EncryptUtil.encodeURL(url);
        String urlDecoded = EncryptUtil.decodeURL(urlEncoded);

        System.out.println(url);
        System.out.println(urlEncoded);
        System.out.println(urlDecoded);*/
    }


}
