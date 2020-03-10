package com.example.ggprototype_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotificationActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick( View v) { openAddReminderActivity();}
        });
    }

    private void openAddReminderActivity() {
        Intent intent = new Intent (this, AddReminderActivity.class);
        startActivity(intent);
     }
}
