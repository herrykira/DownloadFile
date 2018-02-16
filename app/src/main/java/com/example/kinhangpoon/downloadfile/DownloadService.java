package com.example.kinhangpoon.downloadfile;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by KinhangPoon on 15/2/2018.
 */

public class DownloadService extends IntentService {
    public DownloadService() {
        super("downloading");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String urlDownload = intent.getStringExtra("url");
        Log.i("url",urlDownload);
        try {
            URL url = new URL(urlDownload);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            //show a typical 0-100% progress bar
            int fileLength = connection.getContentLength();

            //download the file
            InputStream input = new BufferedInputStream(connection.getInputStream());
//            OutputStream output = new FileOutputStream("/sdcard/demo.mp4");
            OutputStream output = new FileOutputStream("/sdcard/myfile.jpg");


            byte data[] = new byte[1024];
            long total =0;
            int count;
            while((count = input.read(data))!=-1){
                total+=count;
//                //publishing progress...
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        SystemClock.sleep(3000);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(MainActivity.ResponseReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(broadcastIntent);
    }
}
