package com.example.myapplication1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.BatchUpdateException;

public class UpdateInfo extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSubmit;
    private EditText editTextName;
    private EditText editTextProgram;
    private EditText editTextYear;

    private FirebaseAuth    firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        FirebaseApp.initializeApp(this);
        firebaseAuth= firebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        /* new code start
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString().trim();
                    String program = dataSnapshot.child("program").getValue().toString().trim();
                    String year = dataSnapshot.child("Year").getValue().toString().trim();
                    editTextName.setText(name);
                    editTextProgram.setText(program);
                    editTextYear.setText(year);
                }
                else {int d = 1;}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        new code end */
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextProgram= (EditText)findViewById(R.id.editTextProgram);
        editTextYear=(EditText)findViewById(R.id.editTextYear);


        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(this);
    }

    private void saveUserInfo(){
        //saves info
        String name = editTextName.getText().toString().trim();
        String program = editTextProgram.getText().toString().trim();
        String year = editTextYear.getText().toString().trim();
        int y=0;
        try {
            y = Integer.parseInt(year);
        } catch(NumberFormatException nfe) {}

        UserInfo userInfo = new UserInfo(name,program,y);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child("Users").child(user.getUid()).setValue(userInfo);
        Toast.makeText(this,"Information Saved",Toast.LENGTH_LONG).show();

    }
    @Override
    public void onClick(View view) {

        if(view==buttonSubmit){
            //update info
            //open user profile
            saveUserInfo();
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
        }

    }
}
