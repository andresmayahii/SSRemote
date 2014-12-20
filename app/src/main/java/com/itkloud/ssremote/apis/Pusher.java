package com.itkloud.ssremote.apis;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class Pusher {

    private static final String HOST = "api.pusherapp.com";
    private static final String CHARSET = "UTF-8";
    private static final int SUCCESS_STATUS_CODE = 202;
    private final String appId;
    private final String key;
    private final String secret;
    private final HttpClient httpclient;

    public Pusher(String appId, String key, String secret) {
        super();
        httpclient = new DefaultHttpClient();
        this.appId = appId;
        this.key = key;
        this.secret = secret;
    }

    public boolean trigger(String channel, String event,JSONObject json) throws Exception {
        return trigger(channel,event,json,null);
    }

    public boolean trigger(String channel, String event,JSONObject json , String socketId) throws Exception {
        String message = json.toString();
        StringBuilder path = new StringBuilder("/apps/").append(appId).append("/channels/").append(channel).append("/events");

        StringBuilder query = new StringBuilder("auth_key=").append(key)
                .append("&auth_timestamp=").append((System.currentTimeMillis() / 1000))
                .append("&auth_version=1.0")
                .append("&body_md5=").append(md5(message))
                .append("&name=").append(event);

        if(socketId != null) {
            query.append("&socket_id=").append(socketId);
        }

        String signature = sha256(new StringBuilder("POST\n").append(path).append("\n").append(query).toString(), secret);

        String uri = new StringBuilder("http://").append(HOST).append(path).append('?').append(query).append("&auth_signature=").append(signature).toString();

        HttpPost httppost = new HttpPost(uri);
        StringEntity entity = new StringEntity(message, CHARSET);
        entity.setContentType("application/json");
        httppost.setEntity(entity);
        HttpResponse response =  httpclient.execute(httppost);

        if(response.getStatusLine().getStatusCode() == SUCCESS_STATUS_CODE)  return true;
        EntityUtils.toString(response.getEntity());
        return false;
    }

    private static String byteArrayToString(byte[] data){
        BigInteger bigInteger = new BigInteger(1,data);
        String hash = bigInteger.toString(16);

        while(hash.length() < 32 ){
            hash = "0" + hash;
        }

        return hash;
    }

    private static String md5(String string) {
        try {
            byte[] bytesOfMessage = string.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bytesOfMessage);
            return byteArrayToString(digest);
        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException("No HMac SHA256 algorithm");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("No UTF-8");
        }
    }

    private static String sha256(String string, String secret) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec( secret.getBytes(), "HmacSHA256");

            final Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] digest = mac.doFinal(string.getBytes("UTF-8"));
            digest = mac.doFinal(string.getBytes());

            BigInteger bigInteger = new BigInteger(1,digest);
            return String.format("%0" + (digest.length << 1) + "x", bigInteger);

        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException("No HMac SHA256 algorithm");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("No UTF-8");
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Invalid key exception while converting to HMac SHA256");
        }
    }

}