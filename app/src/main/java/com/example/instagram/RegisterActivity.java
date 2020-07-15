package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText name;
    private EditText email;
    private EditText password;
    private Button register;
    private TextView loginUser;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference mRootRef = firebaseDatabase.getReference();
    private FirebaseAuth mAuth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        loginUser = findViewById(R.id.login_user);

        mAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textUsername = username.getText().toString();
                String textName = name.getText().toString();
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();

                if (textUsername.isEmpty() || textName.isEmpty() || textEmail.isEmpty() || textPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                } else if (textPassword.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password too short\nEnter atleast 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(textUsername, textEmail, textName, textPassword);
                }


            }
        });

    }

    private void registerUser(final String username, final String email, final String name, String password) {

        progressDialog.setMessage("Please Wait");

        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("email", email);
                map.put("username", username);
                map.put("id", mAuth.getCurrentUser().getUid());
                map.put("bio", "");
                map.put("imageurl", "default");


                mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Update the profile" +
                                    " for better Experience", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity2.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //in video, clear task is first

                            startActivity(intent);
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Unable to update in database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}