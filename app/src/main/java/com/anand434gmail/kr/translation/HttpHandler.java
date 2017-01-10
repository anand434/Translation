package com.anand434gmail.kr.translation;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Anand_Kumar on 1/10/2017.
 */

public class HttpHandler {

    private static String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler(){
    }

    public String makeServiceCall(String reqUrl){
        String responce = null;

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream in = new BufferedInputStream(conn.getInputStream());
            responce = convertStreamToString(in);

        } catch (MalformedURLException e) {
            Log.e(TAG , "MalformedURLException : " + e.getMessage() );
        } catch (IOException e) {
            Log.e(TAG , "IOException : " + e.getMessage() );
        }


        return responce;
    }

    private String convertStreamToString(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        String line;
        try{
            while ((line = reader.readLine()) != null){
                sb.append(line).append('\n');
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                in.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}
