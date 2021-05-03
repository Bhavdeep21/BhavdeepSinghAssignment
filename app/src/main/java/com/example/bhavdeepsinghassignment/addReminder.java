package com.example.bhavdeepsinghassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class addReminder extends AppCompatActivity {

    EditText tr,r,rd,rde,rh;
    Calendar c,ca;
    AlarmManager am;
    PendingIntent pi;
    int hour,mint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }
        myChannel();
        tr=findViewById(R.id.tr);
        r=findViewById(R.id.r);
        rd=findViewById(R.id.rd);
        rde=findViewById(R.id.rde);
        rh=findViewById(R.id.rh);
        tr.setClickable(true);
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=Calendar.getInstance();
                TimePickerDialog tp=new TimePickerDialog(addReminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour=hourOfDay;
                        mint=minute;
                        tr.setText(hour+" : "+mint);
                        ca=Calendar.getInstance();
                        ca.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        ca.set(Calendar.MINUTE,minute);
                        ca.set(Calendar.SECOND,0);
                    }
                },c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true);
                tp.setTitle("Select Time");
                tp.show();
            }
        });

    }

    void myChannel()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String name="My channel";
            String desc="my channel description";
            int imp= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel nc=new NotificationChannel("notify",name,imp);
            nc.setDescription(desc);
            NotificationManager nw =getSystemService(NotificationManager.class);
            nw.createNotificationChannel(nc);
        }
    }
    public void setRemind(View view) {
        Toast.makeText(getApplicationContext(),"Reminder set",Toast.LENGTH_LONG).show();
        Intent i=new Intent(addReminder.this,alaramReciever.class);
        i.putExtra("head",rh.getText().toString());
        Log.e("tri", ""+rh.getText());
        i.putExtra("desc",rde.getText().toString());
        i.putExtra("repeat",Integer.parseInt(r.getText().toString()));
        i.putExtra("repeatDuration",Integer.parseInt(rd.getText().toString()));
        pi=PendingIntent.getBroadcast(addReminder.this,(int)Calendar.getInstance().getTimeInMillis(),i,0);
        am=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long at=ca.getTimeInMillis();
        am.set(AlarmManager.RTC_WAKEUP,at,pi);
//        am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, at,Integer.parseInt(rd.getText().toString()), pi);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(am!=null)
        {
            am.cancel(pi);
        }
    }
}