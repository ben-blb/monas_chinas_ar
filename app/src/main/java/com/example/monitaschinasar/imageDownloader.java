package com.example.monitaschinasar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class imageDownloader extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url;
        HttpURLConnection httpURLConnection = null;
        try {
            url = new URL(strings[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            Bitmap img = BitmapFactory.decodeStream(inputStream);
            return img;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
