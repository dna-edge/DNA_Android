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

//
//        try {
//            String param = "id=test&password=qwer1234";
//
//            URL reqUrl = new URL(url);
//            urlConn = (HttpsURLConnection) reqUrl.openConnection();
//            urlConn.setRequestMethod("POST");
//            urlConn.setUseCaches(false);
//            urlConn.setDoInput(true);
//            urlConn.setDoOutput(true);
//
//            Log.e("!!!!=", "내용 추가");
//            OutputStream outputStream = urlConn.getOutputStream();
//
//            outputStream.write( param.getBytes("UTF-8") );
//
//            outputStream.flush();
//            outputStream.close();
//
//            Log.e("!!!!=", "접속 시도");
//
//            int resCode = urlConn.getResponseCode();
//            if(resCode != HttpsURLConnection.HTTP_OK){
//                return null;
//            }
//
//            reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
//            String input;
//            StringBuffer sb = new StringBuffer();
//
//            while((input = reader.readLine())!=null){
//                sb.append(input);
//            }
//
//            return sb.toString();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            Log.e("!!!!=","접속 끝");
//            if(urlConn!=null) {
//                urlConn.disconnect();
//            }
//            try {
//                if(reader!=null){
//                    reader.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
    }
}
