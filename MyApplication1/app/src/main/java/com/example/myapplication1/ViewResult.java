package com.example.myapplication1;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewResult extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;



    private Button buttonBack;
    private TextView textViewc1;
    private TextView textViewcname;
    private TextView textViewcredit;
    private TextView textViewc2;
    private TextView textViewcname1;
    private TextView textViewcredit1;
    private TextView textViewc3;
    private TextView textViewcname2;
    private TextView textViewcredit2;

    private TextView grade;
    private TextView grade1;
    private TextView grade2;

    private TextView gpa;

    private TextView textViewname;
    private TextView textViewRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);

        FirebaseApp.initializeApp(this);
        firebaseAuth= firebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        buttonBack= (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);

        textViewc1= (TextView)findViewById(R.id.c1);
        textViewcname= (TextView)findViewById(R.id.cname);
        textViewcredit= (TextView)findViewById(R.id.credit);

        textViewname= (TextView)findViewById(R.id.name);

        textViewc2= (TextView)findViewById(R.id.c2);
        textViewcname1= (TextView)findViewById(R.id.cname1);
        textViewcredit1= (TextView)findViewById(R.id.credit1);

        textViewc3= (TextView)findViewById(R.id.c3);
        textViewcname2= (TextView)findViewById(R.id.cname2);
        textViewcredit2= (TextView)findViewById(R.id.credit2);

        textViewRoll = (TextView)findViewById(R.id.roll);

        grade = (TextView) findViewById(R.id.grade);
        grade1 = (TextView)findViewById(R.id.grade1);
        grade2 = (TextView)findViewById(R.id.grade2);
        gpa = (TextView)findViewById(R.id.gpa);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString().trim();
                    textViewname.setText("Name: "+name);
                }
                else {int d = 1;}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Form").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("Course_1").getValue().toString().trim();
                    textViewc1.setText(name);
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(name);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String name = dataSnapshot.child("name").getValue().toString().trim();
                                textViewcname.setText(name);
                                String credit = dataSnapshot.child("credit").getValue().toString().trim();
                                textViewcredit.setText("Credit "+credit);
                            }
                            else {int d = 1;}
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Result").child(user.getUid()).child(name);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String name = dataSnapshot.child("Grade").getValue().toString().trim();
                                grade.setText("Grade"+ name);
                            }
                            else {int d = 1;}
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {int d = 1;}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // 2nd Course
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Form").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("Course_2").getValue().toString().trim();
                    textViewc2.setText(name);
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(name);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String name = dataSnapshot.child("name").getValue().toString().trim();
                                textViewcname1.setText(name);
                                String credit = dataSnapshot.child("credit").getValue().toString().trim();
                                textViewcredit1.setText("Credit "+ credit);
                            }
                            else {int d = 1;}
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Result").child(user.getUid()).child(name);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String name = dataSnapshot.child("Grade").getValue().toString().trim();
                                grade1.setText("Grade "+name);
                            }
                            else {int d = 1;}
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {int d = 1;}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // 3rd Course
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Form").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("Course_3").getValue().toString().trim();
                    textViewc3.setText(name);
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(name);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String name = dataSnapshot.child("name").getValue().toString().trim();
                                textViewcname2.setText(name);
                                String credit = dataSnapshot.child("credit").getValue().toString().trim();
                                textViewcredit2.setText("Credit " + credit);
                            }
                            else {int d = 1;}
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Result").child(user.getUid()).child(name);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String name = dataSnapshot.child("Grade").getValue().toString().trim();
                                grade2.setText("Grade " + name);
                            }
                            else {int d = 1;}
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {int d = 1;}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Roll number
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Form").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String roll = dataSnapshot.child("roll").getValue().toString().trim();
                    textViewRoll.setText("Roll - "+roll );
                }
                else {int d = 1;}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Result").child(user.getUid()).child("GPA");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String gradepoint = dataSnapshot.child("Grade").getValue().toString().trim();
                    gpa.setText("GPA - "+gradepoint );
                }
                else {int d = 1;}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view==buttonBack){
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
        }
    }
}
