package com.example.monitaschinasar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.monitaschinasar.imageDownloader;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class arViewer extends AppCompatActivity {
    private ArFragment arFragment;
    private ViewRenderable monita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_viewer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent viewData = getIntent();
        String imageUrl = getIntent().getStringExtra("url");

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.monitaFragment);
        arFragment.getPlaneDiscoveryController().hide();
        arFragment.getPlaneDiscoveryController().setInstructionView(null);
        ViewRenderable.builder()
                .setView(this, R.layout.monita_ar)
                .build()
                .thenAccept(renderable ->{
                    ImageView imgView = (ImageView) renderable.getView();
                    imageDownloader imageDownloader = new imageDownloader();
                    try{
                        Bitmap downloadedMonita = imageDownloader.execute(imageUrl).get();
                        imgView.setImageBitmap(downloadedMonita);
                        monita = renderable;
                        Toast.makeText(this, "monitaDownloaded", Toast.LENGTH_SHORT).show();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                })
                .exceptionally(
                    throwable -> {
                        Toast toast =
                                Toast.makeText(this, "Unable to load renderable", Toast.LENGTH_LONG);
                        toast.show();
                        return null;
                });
        arFragment.setOnTapArPlaneListener((HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
            if(monita == null){
                return;
            }
            Anchor anchor = hitResult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());
            TransformableNode monachina = new TransformableNode(arFragment.getTransformationSystem());
            monachina.setParent(anchorNode);
            monachina.setRenderable(monita);
            monachina.select();
        });
    }

}
