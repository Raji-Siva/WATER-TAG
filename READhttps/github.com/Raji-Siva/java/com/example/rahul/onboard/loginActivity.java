package com.example.rahul.onboard;

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
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    private Button mloginBtn;
    private EditText mEmailField;
    private EditText mPassField;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mloginBtn = (Button) findViewById(R.id.btn);
        mEmailField = (EditText) findViewById(R.id.editText3);
        mPassField = (EditText) findViewById(R.id.editText4);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(loginActivity.this, HomeActivity.class));
                }
            }
        };

        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn() {
        String email = mEmailField.getText().toString();
        String pwd = mPassField.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(loginActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        startActivity(new Intent(loginActivity.this, HomeActivity.class));
                       // Toast.makeText(loginActivity.this, "Sign in Problem...", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}