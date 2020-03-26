package com.example.loginapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private TextView mFromFeedBack;
    private ProgressBar mFromProgressBar;

    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;

    private EditText mEmail, mPassword;
    private Button mLinkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFromFeedBack = findViewById(R.id.fromFeedback);
        mFromProgressBar = findViewById(R.id.from_progress_Bar);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mEmail = findViewById(R.id.emailText);
        mPassword = findViewById(R.id.passwordText);
        mLinkButton = findViewById(R.id.linkButton);


        if (mCurrentUser == null) {

            mFromProgressBar.setVisibility(View.VISIBLE);

            mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    mFromFeedBack.setVisibility(View.VISIBLE);
                    if (task.isSuccessful()) {

                        mFromFeedBack.setText("Signed in Anonymously");

                    } else {
                        
                        mFromFeedBack.setText("There Was An Error Signing in");
                    }
                    mFromProgressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
        mLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (mCurrentUser!=null){
                    if(!email.isEmpty() || !password.isEmpty()){
                        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
                        mCurrentUser.linkWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                mFromFeedBack.setVisibility(View.VISIBLE);

                                if (task.isSuccessful()) {
                                    mFromFeedBack.setText("welcome "+ email );

                                } else {
                                    mFromFeedBack.setText("There Was An Error Signing in");
                                }
                                mFromProgressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                }
            }
        });
    }


}

