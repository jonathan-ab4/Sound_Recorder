package com.example.soundrecorder.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.soundrecorder.Database.DBHelper;
import com.example.soundrecorder.Models.RecordingItem;

import java.io.File;
import java.io.IOException;

public class RecordingService extends Service {

    MediaRecorder mediaRecorder;
    long mStartingTimeMillis=0;
    long mElapsedMillis=0;

    File file;

    String filename;

    DBHelper dbHelper;


    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper= new DBHelper(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording();
        return START_STICKY;
    }

    private void startRecording() {

        Long tsLong= System.currentTimeMillis()/1000;
        String ts= tsLong.toString();

        filename = "audio_"+ts;
        file = new File(Environment.getExternalStorageDirectory()+"/MySecondRec"+filename+".mp3");

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(file.getAbsolutePath());
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setAudioChannels(1);

        try
        {
            mediaRecorder.prepare();
            mediaRecorder.start();

            mStartingTimeMillis= System.currentTimeMillis();

        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    private void stopRecording()
    {
        mediaRecorder.stop();
        mElapsedMillis= (System.currentTimeMillis()- mStartingTimeMillis);
        mediaRecorder.release();
        Toast.makeText(getApplicationContext(),"Recording saved at "+file.getAbsolutePath(),Toast.LENGTH_LONG).show();


        RecordingItem recordingItem = new RecordingItem(filename,file.getAbsolutePath(),mElapsedMillis,System.currentTimeMillis());

        dbHelper.addRecording(recordingItem);

    }

    @Override
    public void onDestroy() {

        if(mediaRecorder!=null)
        {
            stopRecording();
        }
        super.onDestroy();
    }
}
