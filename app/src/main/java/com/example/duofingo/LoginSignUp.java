package com.example.duofingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class LoginSignUp extends AppCompatActivity {

    boolean isSignUp;
    Switch loginSignUpSwitch;
    EditText fullName;
    EditText userName;
    EditText password;
    EditText email;
    TextView loginRegisterText;
    Button loginRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        isSignUp = false;
        loginSignUpSwitch = findViewById(R.id.login_signup_toggle);
        fullName = findViewById(R.id.signup_full_name);
        fullName.setVisibility(View.INVISIBLE);
        userName = findViewById(R.id.signup_username);
        userName.setVisibility(View.INVISIBLE);

        password = findViewById(R.id.loginPassword);
        email = findViewById(R.id.loginEmail);
        loginRegisterText = findViewById(R.id.loginRegisterText);
        loginRegisterButton = findViewById(R.id.loginOrRegisterButton);

        loginSignUpSwitch.setOnClickListener(v -> {
            isSignUp = !isSignUp;
            if (isSignUp) {
                fullName.setVisibility(View.VISIBLE);
                userName.setVisibility(View.VISIBLE);
                loginRegisterText.setText("Register");
                loginRegisterButton.setText("Register");
            }
            else {
                fullName.setVisibility(View.INVISIBLE);
                userName.setVisibility(View.INVISIBLE);
                loginRegisterText.setText("Login");
                loginRegisterButton.setText("Login");
            }
        });

        loginRegisterButton.setOnClickListener(v -> {
            if (isSignUp) {
                // do signup things
                if (validateUsername() && validateEmail() && validatePassword() && validateFullName()) {
                    Toast.makeText(LoginSignUp.this, "Registering this user", Toast.LENGTH_SHORT).show();
                    // post to the database
                    openDashboardActivity();
                }

            } else {
                if (validateEmail() && validatePassword()) {
                    // if the information is in the database
                    openDashboardActivity();
                }
            }
        });

    }

    /**
     * Validate the email address passed in by the user
     * @return true if the user has a valid email, false otherwise
     */
    private boolean validateEmail() {

        String emailString = email.getText().toString();

        // if the email is empty
        if (emailString.equals("")) {
            email.setError("Email cannot be empty");
            return false;
        }
        if (!emailString.contains("@")) {
            email.setError("Invalid email address");
            return false;
        }
        return true;
    }

    /**
     * Method to validate the username
     * @return true if valid username
     */
    private boolean validateUsername() {
        if (userName.getText().toString().equals("")) {
            userName.setError("Cannot have empty username");
            return false;
        }
        return true;
    }

    /**
     * Method to validate the fullName of the user signing up
     * @return true if valid fullName,
     */
    private boolean validateFullName() {
        if (fullName.getText().toString().equals("")) {
            fullName.setError("Cannot have empty name");
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        if (password.getText().toString().equals("")) {
            password.setError("password cannot be empty");
            return false;
        }
        return true;
    }

    private void openDashboardActivity() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("userEmail", email.getText().toString());
        intent.putExtra("password", password.getText().toString());
        startActivity(intent);
    }

}