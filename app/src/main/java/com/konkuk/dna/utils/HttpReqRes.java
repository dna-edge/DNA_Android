package com.konkuk.dna.utils;

import android.icu.util.Output;
import android.util.Log;

import com.google.gson.JsonObject;
import com.konkuk.dna.post.Comment;
import com.konkuk.dna.post.Post;
import com.konkuk.dna.utils.dbmanage.Dbhelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.JSONStringer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.konkuk.dna.utils.JsonToObj.PostingJsonToObj;
import static junit.framework.Assert.assertEquals;


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

        HttpsURLConnection urlConn = null;
        BufferedReader reader = null;

        String result=null;
        try {
            HttpClient client = new DefaultHttpClient();
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
        return result;
    }


    /*
     * 베스트챗 받아오기 - post
     * */
    public String requestHttpPostBestChat(String url, String token, Double lng, Double lat, Integer radius){

        HttpsURLConnection urlConn = null;
        BufferedReader reader = null;

        String result=null;
        try {
            HttpClient client = new DefaultHttpClient();
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
     * 사진 람다업로드 후 주소받아오기 - post
     * */
    public static String requestHttpPostLambda(String url, String imgURL){

        /*
         await axios.post(`${AWS_LAMBDA_API_URL}?type=${type}`, formData,
    { headers: { 'Content-Type': 'multipart/form-data' }})
    .then((response) => {result = response});
         */

        //File file = new File(imgURL);

        //TODO: js에서 file으로 넘기는게 무슨 객체인지, 어떤 형식인지 알아야 풀 수 있을 것 같다.
        String result=null;
        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = url+"?type=image";
            HttpPost post = new HttpPost(postURL);
            post.setHeader(HTTP.CONTENT_TYPE, "multipart/form-data");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("image", imgURL));

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
        Log.e("Upload, Get Image", result);
        return result;
    }

    /*
     * get PostsAll = GET
     */
    public String requestHttpGetPostingAll(String url, String token){

        String result = null;

        try{
            HttpClient client = new DefaultHttpClient();
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);

            String getURL = url;
            HttpGet get = new HttpGet(getURL);
            get.setHeader("token", token);

            HttpResponse responseGET = client.execute(get);
            HttpEntity resEntity = responseGET.getEntity();
            if(resEntity != null){
                result = EntityUtils.toString(resEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /*
     * get Posts = GET
     */
    public String requestHttpGetPosting(String url){

        String result = null;

        try{
            HttpClient client = new DefaultHttpClient();
//            HttpParams params = client.getParams();
//            HttpConnectionParams.setConnectionTimeout(params, 5000);
//            HttpConnectionParams.setSoTimeout(params, 5000);
            String getURL = url;
            HttpGet get = new HttpGet(getURL);

            HttpResponse responseGET = client.execute(get);
            HttpEntity resEntity = responseGET.getEntity();
            if(resEntity != null){
                result = EntityUtils.toString(resEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /*
     * write Posts = Post
     */
    public String requestHttpPostWritePosting(String url, Dbhelper dbhelper, Post posting) {
        String result = null;
        JSONObject json = null;

        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = url;
            HttpPost post = new HttpPost(postURL);
            post.setHeader("token", dbhelper.getAccessToken());
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            String lng = String.valueOf(posting.getLongitude());
            String lat = String.valueOf(posting.getLatitude());
            String om = String.valueOf(posting.getOnlyme());

            nameValuePairs.add(new BasicNameValuePair("date", posting.getDate()));
            nameValuePairs.add(new BasicNameValuePair("title", posting.getTitle()));
            nameValuePairs.add(new BasicNameValuePair("contents", posting.getContent()));
            nameValuePairs.add(new BasicNameValuePair("latitude", lat));
            nameValuePairs.add(new BasicNameValuePair("longitude", lng));
            nameValuePairs.add(new BasicNameValuePair("onlyme", om));
            nameValuePairs.add(new BasicNameValuePair("nickname", dbhelper.getMyNickname()));
            nameValuePairs.add(new BasicNameValuePair("avatar", dbhelper.getMyAvatar()));

            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(post);
            HttpEntity resEntity = response.getEntity();
            result = EntityUtils.toString(resEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    /*
     * Post DETAILS = Post
     */
    public String requestHttpPosting(String url, String token, int postCase) {
        String result = null;
//        JSONObject json = null;
//        Post posting;

        switch(postCase) {
            case 1:        // like
                try {
                    HttpClient client = new DefaultHttpClient();
                    String postURL = url;
                    HttpPost post = new HttpPost(postURL);

                    post.setHeader("token", token);

                    HttpResponse response = client.execute(post);
                    HttpEntity resEntity = response.getEntity();
                    result = EntityUtils.toString(resEntity);

                } catch (Exception e) {
                    e.printStackTrace();
                    return result;
                }

                break;

            case 2:        // bookmark
                try {
                    HttpClient client = new DefaultHttpClient();
                    String postURL = url;
                    HttpPost post = new HttpPost(postURL);

                    post.setHeader("token", token);

                    HttpResponse response = client.execute(post);
                    HttpEntity resEntity = response.getEntity();
                    result = EntityUtils.toString(resEntity);

                } catch (Exception e) {
                    e.printStackTrace();
                    return result;
                }

                break;

            case 3:        // unlike
                try {
                    HttpClient client = new DefaultHttpClient();
                    String deleteURL = url;
                    HttpDelete del = new HttpDelete(deleteURL);


                    del.setHeader("token", token);

                    HttpResponse response = client.execute(del);
                    HttpEntity resEntity = response.getEntity();
                    result = EntityUtils.toString(resEntity);

                } catch (Exception e) {
                    e.printStackTrace();
                    return result;
                }

                break;

            case 4:        // dbookmark
                try {
                    HttpClient client = new DefaultHttpClient();
                    String deleteURL = url;
                    HttpDelete del = new HttpDelete(deleteURL);

                    del.setHeader("token", token);

                    HttpResponse response = client.execute(del);
                    HttpEntity resEntity = response.getEntity();
                    result = EntityUtils.toString(resEntity);

                } catch (Exception e) {
                    e.printStackTrace();
                    return result;
                }

                break;
            }
        Log.v("posting httpreqres", "get server result : " + result);

        return result;
    }

    /*
     * write comments = Post
     */
    public String requestHttpPostWritePosting(String url, Dbhelper dbhelper, String content) {
        String result = null;
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        JSONObject json = null;


        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = url;
            HttpPost post = new HttpPost(postURL);
            post.setHeader("token", dbhelper.getAccessToken());
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            Log.v("httpreqres", "contents : " + content + "\n nick : " + dbhelper.getMyNickname() + "\n avatar : " + dbhelper.getMyAvatar());
            nameValuePairs.add(new BasicNameValuePair("rcontents", content));
            nameValuePairs.add(new BasicNameValuePair("nickname", dbhelper.getMyNickname()));
            nameValuePairs.add(new BasicNameValuePair("avatar", dbhelper.getMyAvatar()));
            nameValuePairs.add(new BasicNameValuePair("rdate", sdf.format(dt).toString()));

            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(post);
            HttpEntity resEntity = response.getEntity();
            result = EntityUtils.toString(resEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        Log.v("httpreqres", "result of reply : " + result);
        return result;
    }
}
