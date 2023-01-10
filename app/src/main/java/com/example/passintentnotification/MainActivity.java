package com.example.passintentnotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID= "My Channel";
    private static final int NOTIFICATION_ID = 100;
    private static final int RED_CODE=100;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        // adding on click listner
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification();
            }
        });
    }

    private void addNotification() {
        //For coverting img into bitmap
        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.snpv,null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable; //drawable to bitmapdrawable
        Bitmap LargeIcon = bitmapDrawable.getBitmap();


        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;

        //For Intent in notification
        Intent iNotify = new Intent(getApplicationContext(),NotificationIntent.class);
        iNotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this,RED_CODE,iNotify,PendingIntent.FLAG_MUTABLE);


        //checking device SDK
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            //For Android above Orio
            notification = new Notification.Builder(this)
                    .setLargeIcon(LargeIcon)
                    .setSmallIcon(R.drawable.snpv)  // for android above 8 orio
                    .setContentText("New Message")
                    .setContentIntent(pi)
                    .setSubText("msg from someone")
                    .setChannelId(CHANNEL_ID)  // Channels for particular channel like msg ,backup,group
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"new channel", NotificationManager.IMPORTANCE_HIGH));
        }else {

            // for Android below orio
            notification = new Notification.Builder(this)
                    .setLargeIcon(LargeIcon)
                    .setSmallIcon(R.drawable.snpv) // for android below orio
                    .setContentText("New Message")
                    .setContentIntent(pi)
                    .setSubText("msg from someone")
                    .build();

        }
        nm.notify(NOTIFICATION_ID,notification);
    }
}
