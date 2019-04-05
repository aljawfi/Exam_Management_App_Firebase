package com.example.myapplication1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class FormFill extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSubmit;
    private EditText editTextC1;
    private EditText editTextC2;
    private EditText editTextC3;
    private EditText editTextRoll;

    private EditText editTextName;

    private ListView listView;

    ArrayList<String> myArrayList = new ArrayList<>();

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_fill);

        FirebaseApp.initializeApp(this);
        firebaseAuth= firebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextC1 = (EditText)findViewById(R.id.editTextC1);
        editTextC2= (EditText)findViewById(R.id.editTextC2);
        editTextC3=(EditText)findViewById(R.id.editTextC3);
        editTextName=(EditText)findViewById(R.id.editTextName);
        editTextRoll=(EditText)findViewById(R.id.editTextRoll);
        listView=(ListView) findViewById(R.id.ListViewCourse);


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myArrayList);

        listView.setAdapter(arrayAdapter);


        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                /*long s = dataSnapshot.getChildrenCount();
                String  sa=String.valueOf(s);
                textViews.setText("Number of Courses " +sa);*/
                for (DataSnapshot cd : dataSnapshot.getChildren()) {
                    String s = cd.getKey();
                    myArrayList.add(s);
                    arrayAdapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString().trim();
                    editTextName.setText(name);
                }
                else {int d = 1;}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void formFill(){
        //saves info
        String c1 = editTextC1.getText().toString().trim();
        String c2 = editTextC2.getText().toString().trim();
        String c3 = editTextC3.getText().toString().trim();

        String roll = editTextRoll.getText().toString().trim();
        FormInfo formInfo = new FormInfo(c1,c2,c3,roll);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String block1 ="Not Assigned";
        String room1 = "Not Assigned";
        SeatInfo seatInfo = new SeatInfo(block1,room1);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Seat").child(user.getUid()).setValue(seatInfo);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Form").child(user.getUid()).setValue(formInfo);

        // new code for storing gpa information
        String x ="Not Assigned";
        ResultGrade resultGrade = new ResultGrade(x);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Result").child(user.getUid()).child(c1).setValue(resultGrade);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Result").child(user.getUid()).child(c2).setValue(resultGrade);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Result").child(user.getUid()).child(c3).setValue(resultGrade);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        RollSet rollSet = new RollSet(roll);
        databaseReference.child("Result").child(user.getUid()).child("Roll").setValue(rollSet);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Result").child(user.getUid()).child("GPA").setValue(resultGrade);
        Toast.makeText(this,"Information Saved",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view) {
        if(view==buttonSubmit){
            //update info
            //open user profile
            formFill();
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
        }


    }
}
