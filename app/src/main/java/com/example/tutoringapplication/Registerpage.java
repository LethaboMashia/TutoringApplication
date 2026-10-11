package com.example.tutoringapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registerpage extends AppCompatActivity {

    private UserDbHelper userDb;
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        userDb = new UserDbHelper(this);
        etName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etRegisterEmail);
        etPassword = findViewById(R.id.etRegisterPassword);
        etConfirm = findViewById(R.id.etConfirmPassword);
        Button registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(v -> attemptRegister());
    }

    private void attemptRegister() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirm = etConfirm.getText().toString();

        if (!InputValidator.isValidName(name)) {
            etName.setError(getString(R.string.error_name));
            return;
        }
        if (!InputValidator.isValidEmail(email)) {
            etEmail.setError(getString(R.string.error_email));
            return;
        }
        if (!InputValidator.isValidPassword(password)) {
            etPassword.setError(getString(R.string.error_password_rules));
            return;
        }
        if (!InputValidator.passwordsMatch(password, confirm)) {
            etConfirm.setError(getString(R.string.error_password_mismatch));
            return;
        }

        if (!userDb.registerUser(name, email, password)) {
            etEmail.setError(getString(R.string.error_email_taken));
            return;
        }

        Toast.makeText(this, R.string.account_created, Toast.LENGTH_LONG).show();
        finish(); // returns to the login screen underneath
    }
}
