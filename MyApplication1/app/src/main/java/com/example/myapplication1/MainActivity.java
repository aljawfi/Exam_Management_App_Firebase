package com.example.myapplication1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth    firebaseAuth;
    private Button  buttonRegister;
    private EditText    editTextEmail;
    private EditText    editTextPassword;
    private TextView    textViewSignup;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // new code begin
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("MyNotifications","MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        //new code end

        FirebaseApp.initializeApp(this);
        firebaseAuth= firebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser()!= null)
        {
            FirebaseUser check =firebaseAuth.getCurrentUser();
            String c = check.getEmail().toString().trim();

            String a = "admin@gmail.com";

            if (c.equals(a))
            {
                finish();
                startActivity(new Intent(getApplicationContext(),Chapa.class));
            }
            else {//profile activity here
                finish();
                startActivity(new Intent(this, ProfileActivity.class));
            }
            }

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignup = (TextView)findViewById(R.id.textViewSignup);

        progressDialog = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);

        /*firebaseAuth = firebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!= null)
        {
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }*/
        progressDialog = new ProgressDialog(this);
    }

    private void registerUser(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //User Successfully Registered
                            //Start Profile Activity
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Registration Failed.. Please Try Again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == buttonRegister)
        {
            registerUser();
        }
        if(view== textViewSignup){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

}
