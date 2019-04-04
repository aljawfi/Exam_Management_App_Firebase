package com.example.myapplication1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCourse extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSubmit;

    private EditText editTextCourseName;
    private EditText editTextCredit;
    private EditText editTextId;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        FirebaseApp.initializeApp(this);
        firebaseAuth= firebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextCourseName = (EditText)findViewById(R.id.editTextCourseName);
        editTextCredit= (EditText)findViewById(R.id.editTextCredit);
        editTextId=(EditText)findViewById(R.id.editTextId);


        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(this);
    }

    private void addCourse(){
        String name = editTextCourseName.getText().toString().trim();
        String credit = editTextCredit.getText().toString().trim();
        String id = editTextId.getText().toString().trim();
        int y=0;
        try {
            y = Integer.parseInt(credit);
        } catch(NumberFormatException nfe) {}
        CourseInfo courseInfo = new CourseInfo(name,y);

        // add to firebase
        databaseReference.child("Courses").child(id).setValue(courseInfo);
        Toast.makeText(this,"Information Saved",Toast.LENGTH_LONG).show();

    }
    @Override
    public void onClick(View view) {
        if(view==buttonSubmit){
            //update info
            //open user profile
            addCourse();
            finish();
            startActivity(new Intent(this,Chapa.class));
        }

    }
}
