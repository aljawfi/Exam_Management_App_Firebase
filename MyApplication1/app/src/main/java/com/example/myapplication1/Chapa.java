package com.example.myapplication1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chapa extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private Button  buttonAddCourse;
    private Button  buttonSeatPlan;
    private DatabaseReference databaseReference;
    private TextView textViews;
    private ListView listView;

    ArrayList<String> myArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapa);
        FirebaseApp.initializeApp(this);

        firebaseAuth= firebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        buttonLogout = (Button)findViewById(R.id.buttonLogout);
        buttonAddCourse = (Button)findViewById(R.id.buttonAddCourse);
        buttonSeatPlan = (Button)findViewById(R.id.buttonSeatPlan);
        textViews = (TextView)findViewById(R.id.textViews);
        listView=(ListView) findViewById(R.id.ListViewCourse);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myArrayList);

        listView.setAdapter(arrayAdapter);

        buttonLogout.setOnClickListener(this);
        buttonAddCourse.setOnClickListener(this);
        buttonSeatPlan.setOnClickListener(this);

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

    }

    @Override
    public void onClick(View view) {

        if(view== buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        if (view == buttonAddCourse){
            startActivity(new Intent(this,AddCourse.class));
        }
        if (view == buttonSeatPlan){
            Toast.makeText(this,"Seat Plan",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MakeSeatPlan.class));
        }
    }
}

