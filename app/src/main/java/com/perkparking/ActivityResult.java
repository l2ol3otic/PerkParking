package com.perkparking;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.perkparking.model.place2Cal;
import com.perkparking.service.Tservice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by it02 on 4/4/2560.
 */

public class ActivityResult extends Activity {
    String out;
    int hourDiff;
    int minuteDiff;
    int secondDiff;
    Timer T;
    String time,m,h;
    private DBhelper mHelper;
    private int ID = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        EditText flooretx = (EditText)findViewById(R.id.flooretx);
        flooretx.setEnabled(false);
        EditText placeetx = (EditText)findViewById(R.id.placeetx);
        placeetx.setEnabled(false);
        final EditText timeetx = (EditText)findViewById(R.id.timeetx);
        timeetx.setEnabled(false);
        Button bt = (Button)findViewById(R.id.resultbt);
        mHelper = new DBhelper(this);
        place2Cal mPlace2Call = mHelper.getresult("18");
        flooretx.setText(mPlace2Call.getTimeH());
        placeetx.setText(mPlace2Call.getTimeM());
        h = mPlace2Call.getPlace();
        m = mPlace2Call.getFloor();
        String times = m+h;
        Log.i("All",mPlace2Call.getFloor()+mPlace2Call.getPlace()+mPlace2Call.getTimeH()+mPlace2Call.getTimeM());
        Log.i("h+m",h+m);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("h:mm:ss a");
        try {
            Date date = dateFormat.parse(times);
            Log.e("Timeeeeeeeeeeeee", String.valueOf(date));
            out = dateFormat2.format(date);
            Log.e("Timeeeeeeeeeeeee", out);
        } catch (ParseException e) {

        }

        T =new Timer();
        Calendar calendar2 = Calendar.getInstance();
        SimpleDateFormat formatter2 = new SimpleDateFormat("h:mm:ss a");
        final String time2 = formatter2.format(calendar2.getTime());

        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Calendar calendar1 = Calendar.getInstance();
                        SimpleDateFormat formatter1 = new SimpleDateFormat("h:mm:ss a");
                        time = formatter1.format(calendar1.getTime());
                        //DateFormat df = new SimpleDateFormat("h:mm:ss");
                        try {

                            DateFormat df = new SimpleDateFormat("h:mm:ss a");
                            Date start = df.parse(out);
                            Date end = df.parse(time);

                            long diff = end.getTime() - start.getTime();

                            //int dayDiff = (int) (diff / (24 * 60 * 60 * 1000));
                            hourDiff = (int) (diff / (60 * 60 * 1000) % 24);
                            minuteDiff = (int) (diff / (60 * 1000) % 60);
                            secondDiff = (int) (diff / 1000 % 60);
                           if(10-secondDiff>0){
                               if(10-minuteDiff>0){
                                   if(10-hourDiff>0){
                                       timeetx.setText("0"+hourDiff+":"+"0"+minuteDiff+":"+"0"+secondDiff);
                                   }
                                   else{
                                       timeetx.setText(hourDiff+":"+"0"+minuteDiff+":"+"0"+secondDiff);
                                   }
                               }
                               else  if(10-minuteDiff<0){
                                   if(10-hourDiff>0){
                                       timeetx.setText("0"+hourDiff+":"+minuteDiff+":"+"0"+secondDiff);
                                   }
                                   else{
                                       timeetx.setText(hourDiff+":"+minuteDiff+":"+"0"+secondDiff);
                                   }
                               }
                           }
                           else{
                               if(10-minuteDiff>0){
                                   if(10-hourDiff>0){
                                       timeetx.setText("0"+hourDiff+":"+"0"+minuteDiff+":"+secondDiff);
                                   }
                                   else{
                                       timeetx.setText(hourDiff+":"+"0"+minuteDiff+":"+secondDiff);
                                   }
                               }
                               else  if(10-minuteDiff<0){
                                   if(10-hourDiff>0){
                                       timeetx.setText("0"+hourDiff+":"+minuteDiff+":"+secondDiff);
                                   }
                                   else{
                                       timeetx.setText(hourDiff+":"+minuteDiff+":"+secondDiff);
                                   }
                               }
                           }


                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                });
            }
        }, 1000, 1000);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                processStopService(Tservice.TAG);
                T.cancel();
                int price;
                System.out.println("Hour Diff Totel = " + hourDiff);
                System.out.println("Minute Diff Totel = " + minuteDiff);
                System.out.println("Second Diff Totel = " + secondDiff);
                if(hourDiff-1>0){
                    if(minuteDiff>1||secondDiff>1){
                        price = hourDiff*50;
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(ActivityResult.this);
                        builder.setMessage("ค่าจอดรถของคุณคือ "+ String.valueOf(price+" บาท"));
                        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                        builder.show();
                    }
                    else{
                         price = (hourDiff-1)*50;
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(ActivityResult.this);
                        builder.setMessage("ค่าจอดรถของคุณคือ "+ String.valueOf(price +" บาท"));
                        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                        builder.show();
                    }
                }
                else{
                    if(minuteDiff>1||secondDiff>1){
                         price = 0;
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(ActivityResult.this);
                        builder.setMessage("ค่าจอดรถของคุณคือ "+ String.valueOf(price + " บาท"));
                        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                        builder.show();
                    }
                }


            }
        });

    }
    private void processStopService(final String tag) {
        Intent intent = new Intent(getApplicationContext(), Tservice.class);
        intent.addCategory(tag);
        stopService(intent);
    }

}
