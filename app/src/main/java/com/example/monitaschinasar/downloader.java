package com.example.monitaschinasar;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class downloader extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        String result = "";
        URL url;
        HttpURLConnection httpURLConnection = null;
        try{
            url = new URL(strings[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            int data = inputStreamReader.read();
            while(data != -1){
                char current = (char) data;
                result += current;
                data = inputStreamReader.read();
            }
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            return "Error al descargar";
        }
    }
}
