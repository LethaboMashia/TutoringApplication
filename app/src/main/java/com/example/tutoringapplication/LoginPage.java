package com.example.tutoringapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    private UserDbHelper userDb;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        userDb = new UserDbHelper(this);
        etEmail = findViewById(R.id.etLoginEmail);
        etPassword = findViewById(R.id.etLoginPassword);
        Button loginBtn = findViewById(R.id.loginBtn);
        TextView goToRegister = findViewById(R.id.goToRegisterTV);

        loginBtn.setOnClickListener(v -> attemptLogin());
        goToRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginPage.this, Registerpage.class)));
    }

    private void attemptLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (!InputValidator.isValidEmail(email)) {
            etEmail.setError(getString(R.string.error_email));
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError(getString(R.string.error_password_required));
            return;
        }

        String name = userDb.authenticate(email, password);
        if (name == null) {
            Toast.makeText(this, R.string.error_login_failed, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, LandingPage.class);
        intent.putExtra(LandingPage.EXTRA_NAME, name);
        startActivity(intent);
        finish();
    }
}
