package uk.ac.tees.w9210403.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {


    private EditText RegEmail, RegPassword;
    private Button RegButton;
    private TextView RegPageQuestion;
    private FirebaseAuth mAuth;

    private ProgressDialog loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        RegEmail = findViewById(R.id.RegistrationEmail);
        RegPassword = findViewById(R.id.RegistrationPassword);
        RegButton = findViewById(R.id.RegistrationButton);
        RegPageQuestion =findViewById(R.id.RegistrationPageQuestion);

        RegPageQuestion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =RegEmail.getText().toString().trim();
                String password = RegPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)){
                    RegEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    RegPassword.setError("Password Required");
                    return;
                }else {
                    loader.setMessage("Registration in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent =new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            }else {
                                String error = task.getException().toString();
                                Toast.makeText(RegistrationActivity.this, "Registration Failed" + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }

                        }
                    });
                }


            }

        });
    }
}