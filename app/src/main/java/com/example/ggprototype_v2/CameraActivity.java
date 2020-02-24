package com.example.ggprototype_v2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Logger;


public class CameraActivity extends AppCompatActivity implements Scene.OnUpdateListener{

    private CustomArFragment arFragment;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdate);

        textView = findViewById(R.id.trackingStatus);

    }

    /***
     * Establishing an Augmented Image Database for use with AR CORE
     * @param config see ArCore Documentation for details
     * @param session see ArCore Documentation for details
     *      First this method creates All images as objects of type
     *      Bitmap from the android.graphics package.
     *
     *      Then the method adds those Images to the augmented image database
     */
    public  void setUpDatabase(Config config, Session session) {

        InputStream dbStream = getResources().openRawResource(R.raw.gg);

        try {
            AugmentedImageDatabase aid = AugmentedImageDatabase.deserialize(session,dbStream);
            config.setAugmentedImageDatabase(aid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdate(FrameTime frameTime) {

        Frame frame = arFragment.getArSceneView().getArFrame();
        Collection<AugmentedImage> images = frame.getUpdatedTrackables(AugmentedImage.class);

        for (AugmentedImage image : images){
            if (image.getTrackingMethod() == AugmentedImage.TrackingMethod.FULL_TRACKING) {
                Anchor anchor = image.createAnchor(image.getCenterPose());
                switch (image.getName()){

                    case "brother.jpg":
                        createModel(anchor, "beer");
                        textView.setText("brother is visible");
                        break;
                    case "bb.jpg":
                        textView.setText("bb is visible");
                        break;
                    case "cat.jpg":
                        createModel(anchor, "cat");
                        textView.setText("cat is visible");
                        break;
                    case "dog.jpg":
                        createModel(anchor, "dog");
                        textView.setText("dog is visible");
                        break;
                    case "flowers.jpg":
                        createModel(anchor, "flowers");
                        textView.setText("flowers is visible");
                        break;
                }
            }
        }
    }

    private void createModel(Anchor anchor, String modelName) {
        String selection = modelName.toLowerCase();

        switch (selection){
            case "beer":
                ModelRenderable.builder()
                        .setSource(this, Uri.parse("model.sfb"))
                        .build()
                        .thenAccept(modelRenderable -> placeModel(modelRenderable, anchor));
                break;
            case "dog":
                ModelRenderable.builder()
                        .setSource(this, Uri.parse("Mesh_Beagle.sfb"))
                        .build()
                        .thenAccept(modelRenderable -> placeModel(modelRenderable, anchor));
                break;

            case "cat":
                ModelRenderable.builder()
                        .setSource(this, Uri.parse("Mesh_Kitten.sfb"))
                        .build()
                        .thenAccept(modelRenderable -> placeModel(modelRenderable, anchor));
                break;
            default:
                Toast.makeText(this, "Error: no model was called or Incorrect model name was passed", Toast.LENGTH_LONG).show();

                System.out.println("Error: no model was called. Expected: lower case string matching a model.sfb file. Received: "+ selection  );
        }




    }

    private void placeModel(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }
}