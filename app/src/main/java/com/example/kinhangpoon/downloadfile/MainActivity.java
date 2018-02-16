package com.example.kinhangpoon.downloadfile;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ResponseReceiver receiver;
    Button DownloadButton;
    ProgressDialog mProgressDialog;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);

        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);

        // instantiate and register the receiver
        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);

        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver,filter);

        DownloadButton = findViewById(R.id.buttonDownload);
        DownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                Intent i = new Intent(MainActivity.this,DownloadService.class);
                i.putExtra("url","http://rjtmobile.com/ansari/rjt_music/admin/uploads/photo_story/Hrithik.jpg");
//                i.putExtra("url","http://androhub.com/demo/demo.mp4");
                startService(i);
            }
        });
    }
    public class ResponseReceiver extends BroadcastReceiver{
        public final static String ACTION_RESP ="com.example.kinhangpoon.downloadfile";
        @Override
        public void onReceive(Context context, Intent intent) {
            mProgressDialog.dismiss();
            Toast.makeText(MainActivity.this,"Finish downloading",Toast.LENGTH_LONG).show();

            String path = Environment.getExternalStorageDirectory().toString()+"/myfile.jpg";
            Log.i("path",path);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            imageView.setImageBitmap(bitmap);
        }
    }
}
