package com.konkuk.dna.httpjson;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class HttpReqRes {
    public String requestHttpPostLogin(String url,String uid,String upassword){

        HttpsURLConnection urlConn = null;
        BufferedReader reader = null;

        String result=null;
        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = url;
            HttpPost post = new HttpPost(postURL);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", uid));
            params.add(new BasicNameValuePair("password", upassword));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePOST = client.execute(post);
            HttpEntity resEntity = responsePOST.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity);
                Log.i("RESPONSE", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
