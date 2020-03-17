package com.example.leon.baspi;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


import java.io.Console;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //Firebase Database
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference keyRef;

    //Declare Views
    TextView tvValue1;
    TextView tvValue2;
    TextView tvValue3;
    TextView tvValue4;
    TextView tvValue5;
    TextView tvValue6;
    TextView tvValue7;
    TextView tvValue8;
    TextView tvValue9;
    TextView tvValue10;
    TextView tvLastUpdated;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Android Hide Action Bar From: https://stackoverflow.com/questions/19545370/android-how-to-hide-actionbar-on-certain-activities
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Leons Controls");
        //actionBar.hide(); //Hide Action Bar




        //Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://baspi-b1945.firebaseio.com/");
        //keyRef = databaseReference.child("values");


        tvValue1 = findViewById(R.id.tvValue1);
        tvValue2 = findViewById(R.id.tvValue2);
        tvValue3 = findViewById(R.id.tvValue3);
        tvValue4 = findViewById(R.id.tvValue4);
        tvValue5 = findViewById(R.id.tvValue5);
        tvValue6 = findViewById(R.id.tvValue6);
        tvValue7 = findViewById(R.id.tvValue7);
        tvValue8 = findViewById(R.id.tvValue8);
        tvValue9 = findViewById(R.id.tvValue9);
        tvValue10 = findViewById(R.id.tvValue10);
        tvLastUpdated = findViewById(R.id.tvLastUpdated);


        getContents();
    }

    public void getContents() {
        //Get contents from Firebase into String From : https://www.youtube.com/watch?v=WDGmpvKpHyw
        databaseReference.addValueEventListener(new ValueEventListener() { //SingleValueEvent Listener to prevent the append method causing duplicate entries
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                //foodList.clear(); //Clear foodlist before adding items again
                //Get ID From: https://stackoverflow.com/questions/43975734/get-parent-firebase-android
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String value1 = ds.child("radTS").getValue().toString();
                    String value2 = ds.child("oat").getValue().toString() + " °C";
                    String value3 = ds.child("frostStatus").getValue().toString();
                    String value4 = ds.child("boilerFlow").getValue().toString() + " °C";
                    String value5 = ds.child("boilerReturn").getValue().toString() + " °C";
                    String value6 = ds.child("dhwFlow").getValue().toString() + " °C";
                    String value7 = ds.child("boiler01Status").getValue().toString();
                    String value8 = ds.child("boiler02Status").getValue().toString();
                    String value9 = ds.child("lphwPressure").getValue().toString() + " Bar";
                    String value10 = ds.child("radMixValveFeedback").getValue().toString() + " %";


                    //ModelValues values = new ModelValues(value1,value2,value3,value4,value5,value6,value7,value8,value9,value10);

                    tvValue1.setText(value1);
                    if(value1.contains("1")){
                        tvValue1.setText("ON");
                    }
                    else{
                        tvValue1.setText("OFF");
                    }
                    tvValue2.setText(value2);
                    tvValue3.setText(value3);
                    tvValue4.setText(value4);
                    tvValue5.setText(value5);
                    tvValue6.setText(value6);

                    if(value7.contains("0")){
                        tvValue7.setText("OK");
                        tvValue7.setTextColor(Color.parseColor("#008000"));
                    }
                    else{
                        tvValue7.setText("Fault");
                        tvValue7.setTextColor(Color.parseColor("#8B0000"));
                    }


                    if(value8.contains("0")){
                        tvValue8.setText("OK");
                        tvValue8.setTextColor(Color.parseColor("#008000"));
                    }
                    else{
                        tvValue8.setText("Fault");
                        tvValue8.setTextColor(Color.parseColor("#8B0000"));
                    }

                    tvValue9.setText(value9);
                    tvValue10.setText(value10);


                    Date currentTime;
                    currentTime = Calendar.getInstance().getTime();
                    tvLastUpdated.setText("Last Updated: " + currentTime.toString());

                }

            }

            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });
    }


}
