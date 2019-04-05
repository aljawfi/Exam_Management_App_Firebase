package com.example.myapplication1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MakeSeatPlan extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextBlock;
    private EditText editTextRoom;

    private EditText editTextBlock1;
    private EditText editTextRoom1;

    private EditText editTextBlock2;
    private EditText editTextRoom2;

    private Button buttonSubmit;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_seat_plan);

        FirebaseApp.initializeApp(this);


        editTextBlock= (EditText)findViewById(R.id.editTextBlock);
        editTextRoom= (EditText)findViewById(R.id.editTextRoom);

        editTextBlock1= (EditText)findViewById(R.id.editTextBlock1);
        editTextRoom1= (EditText)findViewById(R.id.editTextRoom1);

        editTextBlock2= (EditText)findViewById(R.id.editTextBlock2);
        editTextRoom2= (EditText)findViewById(R.id.editTextRoom2);

        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(this);



    }
    private void addSeat(final String s ,String roll){
                String block = editTextBlock.getText().toString().trim();
                String room = editTextRoom.getText().toString().trim();
        String block1 = editTextBlock1.getText().toString().trim();
        String room1 = editTextRoom1.getText().toString().trim();
        String block2 = editTextBlock2.getText().toString().trim();
        String room2 = editTextRoom2.getText().toString().trim();
                int y=0;
                 try {
                         y = Integer.parseInt(roll);
                     } catch(NumberFormatException nfe) {}
                     if (y>=1 && y<=20){
                SeatInfo data = new SeatInfo(block,room);
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Seat").child(s).setValue(data);}

        if (y>=21 && y<=40){
            SeatInfo data = new SeatInfo(block1,room1);
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Seat").child(s).setValue(data);}

        if (y>=41 && y<=60){
            SeatInfo data = new SeatInfo(block2,room2);
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Seat").child(s).setValue(data);}
    }

    @Override
    public void onClick(View view) {

        if(view==buttonSubmit){
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Form");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot cd : dataSnapshot.getChildren()) {
                        String s = cd.getKey();
                        String roll = cd.child("roll").getValue().toString().trim();
                        addSeat(s,roll);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            finish();
            startActivity(new Intent(this,Chapa.class));
        }

    }
}
