package com.example.ggprototype_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class CameraActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private ModelRenderable sphereRenderable;
    private ModelRenderable beerRenderable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //assigning the AR fragment created in activity_camera.xml to arFragment variable
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        //Blue Sphere Object created
        MaterialFactory.makeOpaqueWithColor(this,new Color(android.graphics.Color.BLUE))
                .thenAccept(material -> {sphereRenderable = ShapeFactory.makeSphere(0.1f, new Vector3(0.0f, 0.15f, 0.0f), material);});

        //Beer object
        ModelRenderable.builder()
                .setSource(this, Uri.parse("beer.sfb"))
                .build()
                .thenAccept(modelRenderable -> beerRenderable = modelRenderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Error: something went wrong with the 3D asset", Toast.LENGTH_LONG).show();
                    return null;
                });

        //Anchor code
        arFragment.setOnTapArPlaneListener(
                ((HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (beerRenderable == null)
                        return;
                    if (plane.getType() != Plane.Type.HORIZONTAL_UPWARD_FACING)
                        return;

                    //Anchor
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    //sphere TransformableNode
//                    TransformableNode sphere = new TransformableNode(arFragment.getTransformationSystem());
//                    sphere.setParent(anchorNode);
//                    sphere.setRenderable(sphereRenderable);
//                    sphere.select();

                    //create beer TransformableNode
                    TransformableNode beer = new TransformableNode(arFragment.getTransformationSystem());
                    beer.setParent(anchorNode);
                    beer.setRenderable(beerRenderable);
                    beer.select();
                })
        );
    }
}
