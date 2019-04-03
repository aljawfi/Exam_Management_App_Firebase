package com.example.myapplication1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button  buttonLogin;
    private EditText    editTextEmail;
    private EditText    editTextPassword;
    private TextView    textViewRegister;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);
        firebaseAuth= firebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser()!= null) {
            FirebaseUser check =firebaseAuth.getCurrentUser();
            String c = check.getEmail().toString().trim();
            String a = "admin@gmail.com";
            if (c.equals(a)) {
                finish();
                startActivity(new Intent(getApplicationContext(), Chapa.class));
            } else {
                //profile activity here
                finish();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        }

        progressDialog = new ProgressDialog(this);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);
        textViewRegister = (TextView) findViewById(R.id.textViewRegister);

        buttonLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
    }

    private void loginUser(){
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Login User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            //Login Successful
                            String a = "admin@gmail.com";
                            if (email.equals(a))
                            {
                                finish();
                                startActivity(new Intent(getApplicationContext(),Chapa.class));
                            }
                            else {
                                finish();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            }
                            }

                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view==buttonLogin)
        {
            loginUser();
        }
        if(view == textViewRegister){
            //display register activity
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
