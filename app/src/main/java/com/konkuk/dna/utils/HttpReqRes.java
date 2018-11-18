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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
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

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import static com.konkuk.dna.utils.JsonToObj.PostingJsonToObj;
import static junit.framework.Assert.assertEquals;


public class HttpReqRes {

    /*
     * getAuthToken - GET
     * */
    public String requestHttpGETAuth(String url, String refreshToken) {

        String result = null;
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
    public String requestHttpPostLogin(String url, String uid, String upassword) {

        HttpsURLConnection urlConn = null;
        BufferedReader reader = null;

        String result = null;
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
            if(responsePOST.getStatusLine().getStatusCode()==200){
                HttpEntity resEntity = responsePOST.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity);
                    Log.i("RESPONSE", result);
                }
            }else{
                result = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
     * 전체 메세지 받아오기 - post
     * */
    public String requestHttpPostMsgAll(String url, String token, Double lng, Double lat, Integer radius) {

        String result = null;
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
        Log.e("requestHttpPostMsgAll", "res: " + result);
        return result;
    }


    /*
     * 베스트챗 받아오기 - post
     * */
    public String requestHttpPostBestChat(String url, String token, Double lng, Double lat, Integer radius) {

        String result = null;
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
    public String requestHttpGETDMRooms(String url, String accessToken) {

        String result = null;
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
    public String requestHttpGETDmMsgs(String url, String accessToken) {

        String result = null;
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
    public static String requestHttpGETUserInfo(String url, String accessToken) {

        String result = null;
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
    public static String requestHttpPutSetting(String url, String token, int radius, int anonymity, int searchable) {

        String result = null;
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
    public static String requestHttpPostLambda(String requrl, String imgURL) {

        /*
         await axios.post(`${AWS_LAMBDA_API_URL}?type=${type}`, formData,
    { headers: { 'Content-Type': 'multipart/form-data' }})
    .then((response) => {result = response});
         */
        //TODO: js에서 file으로 넘기는게 무슨 객체인지, 어떤 형식인지 알아야 풀 수 있을 것 같다.
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        InputStream inputStream = null;

        String twoHyphens = "--";
        //String boundary =  "*****"+Long.toString(System.currentTimeMillis())+"*****";
        String boundary =  "---------------------------"+Long.toString(System.currentTimeMillis());
        Log.e("boundary", boundary);
        String lineEnd = "\r\n";

        String result = "";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;

        String[] q = imgURL.split("/");
        int idx = q.length - 1;
        String ext = android.webkit.MimeTypeMap.getFileExtensionFromUrl(imgURL);

        try {
            File file = new File(imgURL);
            FileInputStream fileInputStream = new FileInputStream(file);

            URL url = new URL(requrl+"?type=image");
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            //connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"" + "image" + "\"; filename=\"" + q[idx] +"\"" + lineEnd);
            outputStream.writeBytes("Content-Type: image/" + ext + lineEnd);
            outputStream.writeBytes(lineEnd);
            //Log.e("Content-type", q[idx]);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while(bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            outputStream.writeBytes(lineEnd);

            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            inputStream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            result = sb.toString();

            fileInputStream.close();
            inputStream.close();
            outputStream.flush();
            outputStream.close();

            return result;
        } catch(Exception e) {
            Log.e("MultipartRequest","Multipart Form Upload Error");
            e.printStackTrace();
            return "error";
        }

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
