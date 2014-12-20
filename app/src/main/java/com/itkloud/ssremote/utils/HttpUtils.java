package com.itkloud.ssremote.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by andressh on 20/12/14.
 */
public class HttpUtils {

    public static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }


    public static String getUrl(String url, Map<String,Object> parameters) {
        StringBuilder sb = new StringBuilder(url);
        boolean isFirst = true;
        for(Map.Entry<String,Object> e:parameters.entrySet()) {
            if(isFirst) {
                sb.append('?');
                isFirst = false;
            } else sb.append('&');
            sb.append(e.getKey());
            sb.append('=');
            sb.append(e.getValue().toString());
        }
        return sb.toString();
    }
}
