package com.anees.callrecoder;

import android.app.Service;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class CallRecordingService extends Service {

    private MediaRecorder rec;
    private boolean recordstarted;
    private File file;
    String path="/sdcard/alarms/";


    @androidx.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);

        Date date = new Date();
        CharSequence sdf = android.text.format.DateFormat.format("MM-dd-yy-hh-mm-ss" , date.getTime());
         rec = new MediaRecorder();
         rec.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
         rec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
         rec.setOutputFile(file.getAbsolutePath()+"/"+sdf+"rec.3gp");
         rec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        TelephonyManager manager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        manager.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
           //   super.onCallStateChanged(state, phoneNumber){

                    if(TelephonyManager.CALL_STATE_IDLE==state && rec==null){
                        rec.stop();
                        rec.reset();
                        rec.release();
                        recordstarted=false;
                       // stopSelf();

                    }else if(TelephonyManager.CALL_STATE_OFFHOOK==state){
                        try {
                            rec.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        rec.start();
                        recordstarted=true;


                    }

                }

            //    }

            }, PhoneStateListener.LISTEN_CALL_STATE);
        return START_STICKY;


    }


       // Log.i(getString(R.string.app_name),"Activity Started");
       // stopSelf();
      //  Toast.makeText(this,"Service Started",Toast.LENGTH_LONG).show();
   //     return super.onStartCommand(intent, flags, startId);
//   }

    @Override
    public void onDestroy() {
        Log.i(getString(R.string.app_name),"Activity Stoped");
        super.onDestroy();
    }
}
