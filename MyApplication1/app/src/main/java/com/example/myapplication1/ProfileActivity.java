package com.example.myapplication1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button  buttonLogout;
    private Button  buttonUpdateInfo;
    private TextView textViewUserEmail;
    private DatabaseReference databaseReference;

    private Button buttonFormFill;
    private Button buttonViewForm;
    private Button buttonViewResult;
    private TextView textViewUserName;
    private TextView textViewProgram;
    private TextView textViewYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseApp.initializeApp(this);
        firebaseAuth= firebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();


        if (firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        textViewUserEmail=(TextView)findViewById(R.id.textViewUserEmail);
        textViewUserName=(TextView) findViewById(R.id.textViewUserName);
        textViewProgram=(TextView) findViewById(R.id.textViewProgram);
        textViewYear=(TextView) findViewById(R.id.textViewYear);

        buttonLogout= (Button)findViewById(R.id.buttonLogout);
        buttonUpdateInfo= (Button)findViewById(R.id.buttonUpdateInfo);
        buttonFormFill = (Button)findViewById(R.id.buttonFillForm);
        buttonViewForm= (Button) findViewById(R.id.buttonViewForm);
        buttonViewResult= (Button) findViewById(R.id.buttonViewResult);

        textViewUserEmail.setText("Welcome "+user.getEmail());

        buttonLogout.setOnClickListener(this);
        buttonUpdateInfo.setOnClickListener(this);
        buttonFormFill.setOnClickListener(this);
        buttonViewForm.setOnClickListener(this);
        buttonViewResult.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString().trim();
                    String program = dataSnapshot.child("program").getValue().toString().trim();
                    String year = dataSnapshot.child("Year").getValue().toString().trim();
                    textViewUserName.setText("Name: "+name);
                    textViewProgram.setText("Program: "+program);
                    textViewYear.setText("Year: "+year);
                }
                else {int d = 1;}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
/*
    private void showData(DataSnapshot  dataSnapshot){
        for(DataSnapshot ds: dataSnapshot.getChildren()){
            UserInfo uinfo = new UserInfo();
            uinfo.setName(ds.child(userId).getValue(UserInfo.class).getName());
            uinfo.setProgram(ds.child(userId).getValue(UserInfo.class).getProgram());
            uinfo.setYear(ds.child(userId).getValue(UserInfo.class).getYear());
            //display all the information
            textViewUserName.setText(uinfo.getName());
            textViewProgram.setText(uinfo.getProgram());
            textViewYear.setText(uinfo.getYear());
        }
    }*/
    @Override
    public void onClick(View view) {
        if(view== buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        if (view == buttonUpdateInfo){
            startActivity(new Intent(this,UpdateInfo.class));
        }
        if(view==buttonFormFill){
            startActivity(new Intent(this,FormFill.class));
        }
        if(view==buttonViewForm){
            startActivity(new Intent(this,FormView.class));
        }
        if(view==buttonViewResult){
            startActivity(new Intent(this,ViewResult.class));
        }

    }
}
