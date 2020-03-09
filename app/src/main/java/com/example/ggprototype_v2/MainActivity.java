package com.example.ggprototype_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button cameraButton;
    private Button notificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraButton = (Button) findViewById(R.id.openCameraActivityButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraActivity();
            }
        });

        notificationButton = (Button) findViewById(R.id.openNotificationActivityButton);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotificationActivity();
            }
        });

    }

    /*
    * Method for changing to camera activity
    * */
    public void openCameraActivity(){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    /*
     * Method for changing to notification activity
     * */
    public void openNotificationActivity(){
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }
}
