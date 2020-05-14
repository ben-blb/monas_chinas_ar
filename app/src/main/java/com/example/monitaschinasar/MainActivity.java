package com.example.monitaschinasar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.monitaschinasar.downloader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView monitas;
    private ArrayList<String> pathMonitas = new ArrayList<>();
    private ArrayList<String> namesMonitas = new ArrayList<>();
    private ArrayAdapter<String> monitasAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monitas = findViewById(R.id.monitasList);
        downloader JsonDownload = new downloader();
        String jsonList = "";
        try{
            jsonList = JsonDownload.execute("http://192.168.0.5/chineasemonas/").get();
            JSONArray monitasArray = new JSONArray(jsonList);
            int i = 0;
            while(i < monitasArray.length()){
                JSONObject currentMonita = new JSONObject(monitasArray.getString(i));
                pathMonitas.add(currentMonita.get("pathName").toString());
                namesMonitas.add(currentMonita.get("name").toString());
                i++;
            }
            monitasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, namesMonitas);
            monitas.setAdapter(monitasAdapter);
            monitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String path = "http://192.168.0.5/chineasemonas/" + pathMonitas.get(i);
                    Intent arViewerIntent = new Intent(getApplicationContext(), arViewer.class);
                    arViewerIntent.putExtra("url", path);
                    startActivity(arViewerIntent);
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
