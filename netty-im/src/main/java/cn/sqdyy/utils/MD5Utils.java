package cn.sqdyy.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * @author <a href="blog.sqdyy.cn">神奇的鸭鸭</a>
 * @ClassName MD5Utils
 * @Description MD5Utils
 * @Date: 2018/11/30 13:39
 * @Version 1.0.0
 */
public class MD5Utils {
    /**
     * @Description: 对字符串进行md5加密
     */
    public static String getMD5Str(String strValue) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
        return newstr;
    }

    public static void main(String[] args) {
        try {
            String md5 = getMD5Str("sqdyy");
            System.out.println(md5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
