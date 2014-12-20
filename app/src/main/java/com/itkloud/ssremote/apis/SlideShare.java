package com.itkloud.ssremote.apis;

import android.util.Log;

import com.itkloud.ssremote.config.CF;
import com.itkloud.ssremote.dto.Account;
import com.itkloud.ssremote.dto.ResultItems;
import com.itkloud.ssremote.utils.HttpUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andressh on 20/12/14.
 */
public class SlideShare {

    private static final String TAG = "SlideShare";

    private Account account;
    private final HttpClient httpclient;

    public SlideShare(Account account) {
        this.account = account;
        httpclient = new DefaultHttpClient(); //TODO manejar el nuevo cliente http de android
    }

    /*
        Si inicia con @ se pide el usuario
        Si inicia con # se pide el tag
        Si no se pide la busqueda

     */
    public ResultItems getResultByCommand(String command,int limit, int offset, String username, String password) {
        Log.d(TAG,"inicia el getResult");

        int type = CF.SEARCH_TYPE;
        String url = CF.SS_SEARCH_URL;
        Map<String,Object> parameters = new HashMap<String,Object>();

        if(command.startsWith("@")) {
            type = CF.USER_TYPE;
            command = command.substring(1,command.length());
            url = CF.SS_USER_URL;
        }
        if(command.startsWith("#")) {
            type = CF.TAG_TYPE;
            command = command.substring(1,command.length());
            url = CF.SS_TAG_URL;
        }

        if(type == CF.USER_TYPE) {
            parameters.put("username_for", command);
            if(username != null && password != null) {
                parameters.put("username",username);
                parameters.put("password",password);
            }
            parameters.put("limit",limit);
            parameters.put("offset",offset);
            parameters.put("detail", 1);
        }

        String t = String.valueOf(System.currentTimeMillis() / 1000);
        parameters.put("api_key", account.getUser());
        parameters.put("ts",t);
        try { parameters.put("hash",HttpUtils.sha1(account.getPassword() + t)); } catch(Exception e){Log.e(TAG,"error",e);}

        String ssUrl = HttpUtils.getUrl(url, parameters);

        Log.d(TAG,"Se imprimira el resultado tipo " + type + " con el valor " + command);
        Log.d(TAG,ssUrl);

        HttpPost httpPost = new HttpPost(ssUrl);
        try {
            HttpResponse response = httpclient.execute(httpPost);
            String body = EntityUtils.toString(response.getEntity());
            Log.d(TAG,body);
        } catch(Exception e) {
            Log.e(TAG,"error",e);
        }


        return null;
    }




}
