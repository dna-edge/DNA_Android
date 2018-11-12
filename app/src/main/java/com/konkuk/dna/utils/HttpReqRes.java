package com.konkuk.dna.utils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class HttpReqRes {

    /*
     * getAuthToken - GET
     * */
    public String requestHttpGETAuth(String url, String refreshToken){

        String result=null;
        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = url;
            HttpGet get = new HttpGet(postURL);
            get.setHeader("token", refreshToken);
            //HttpPost post = new HttpPost(postURL);
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("id", uid));
//            params.add(new BasicNameValuePair("password", upassword));
//            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
//            post.setEntity(ent);

            HttpResponse responseGET = client.execute(get);
            HttpEntity resEntity = responseGET.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /*
    * Login - POST
    * */
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

    /*
    * 전체 메세지 받아오기 - post
    * */
    public String requestHttpPostMsgAll(String url, String token, Double lng, Double lat, Integer radius){

        String result=null;
        try {
            HttpClient client = new DefaultHttpClient();
            client.getParams().setParameter("http.protocol.expect-continue", false);
            client.getParams().setParameter("http.connection.timeout", 2000);
            client.getParams().setParameter("http.socket.timeout", 2000);

            String postURL = url;
            HttpPost post = new HttpPost(postURL);
            post.setHeader("token", token);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("lng", lng.toString()));
            params.add(new BasicNameValuePair("lat", lat.toString()));
            params.add(new BasicNameValuePair("radius", radius.toString()));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePOST = client.execute(post);
            HttpEntity resEntity = responsePOST.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("requestHttpPostMsgAll", "res: "+result);
        return result;
    }


    /*
     * 베스트챗 받아오기 - post
     * */
    public String requestHttpPostBestChat(String url, String token, Double lng, Double lat, Integer radius){

        String result=null;
        try {
            HttpClient client = new DefaultHttpClient();
            client.getParams().setParameter("http.protocol.expect-continue", false);
            client.getParams().setParameter("http.connection.timeout", 2000);
            client.getParams().setParameter("http.socket.timeout", 2000);

            String postURL = url;
            HttpPost post = new HttpPost(postURL);
            post.setHeader("token", token);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("lng", lng.toString()));
            params.add(new BasicNameValuePair("lat", lat.toString()));
            params.add(new BasicNameValuePair("radius", radius.toString()));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePOST = client.execute(post);
            HttpEntity resEntity = responsePOST.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.e("requestHttpPostBestChat", "res: "+result);
        return result;
    }


    /*
     * get DM Rooms- GET
     * */
    public String requestHttpGETDMRooms(String url, String accessToken){

        String result=null;
        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = url;
            HttpGet get = new HttpGet(postURL);
            get.setHeader("token", accessToken);

            HttpResponse responseGET = client.execute(get);
            HttpEntity resEntity = responseGET.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
     * get DM Messages- GET
     * */
    public String requestHttpGETDmMsgs(String url, String accessToken){

        String result=null;
        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = url;
            HttpGet get = new HttpGet(postURL);
            get.setHeader("token", accessToken);

            HttpResponse responseGET = client.execute(get);
            HttpEntity resEntity = responseGET.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
     * get DM Messages- GET
     * */
    public static String requestHttpGETUserInfo(String url, String accessToken){

        String result=null;
        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = url;
            HttpGet get = new HttpGet(postURL);
            get.setHeader("token", accessToken);

            HttpResponse responseGET = client.execute(get);
            HttpEntity resEntity = responseGET.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /*
     * 채팅 환경설정 하기 - put
     * */
    public static String requestHttpPutSetting(String url, String token, int radius, int anonymity, int searchable){

        String result=null;
        try {
            HttpClient client = new DefaultHttpClient();
            String putURL = url;
            HttpPut put = new HttpPut(putURL);
            put.setHeader("token", token);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("radius", String.valueOf(radius)));
            params.add(new BasicNameValuePair("anonymity", String.valueOf(anonymity)));
            params.add(new BasicNameValuePair("searchable", String.valueOf(searchable)));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            put.setEntity(ent);

            HttpResponse responsePUT = client.execute(put);
            HttpEntity resEntity = responsePUT.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
     * 사진 람다업로드 후 주소받아오기 - post
     * */
    public static String requestHttpPostLambda(String url, String imgURL){

        /*
         await axios.post(`${AWS_LAMBDA_API_URL}?type=${type}`, formData,
    { headers: { 'Content-Type': 'multipart/form-data' }})
    .then((response) => {result = response});
         */
        Log.e("Lambdahttp", imgURL);

        //TODO: js에서 file으로 넘기는게 무슨 객체인지, 어떤 형식인지 알아야 풀 수 있을 것 같다.
        String result=null;
        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = url;
            HttpPost post = new HttpPost(postURL);
            post.setHeader("Content-Type", "multipart/form-data");

            File file = new File(imgURL);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("image", new FileBody(file, "application/octet"));
            post.setEntity(builder.build());

            HttpResponse responsePOST = client.execute(post);
            Log.e("HttpResponse", responsePOST.getStatusLine()+"");
            HttpEntity resEntity = responsePOST.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
