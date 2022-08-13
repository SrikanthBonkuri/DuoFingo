package com.example.duofingo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;

public class LoginSignUp extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DB";
    private boolean isLoginSuccessful = false;
    private String dbPassword;

    boolean isSignUp;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch loginSignUpSwitch;
    EditText fullName;
    EditText userName;
    EditText password;
    EditText email;
    TextView loginRegisterText;
    TextView loginAdditionalInformation;
    Button loginRegisterButton;
    ProgressBar progressBar;

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
        progressBar = findViewById(R.id.loginProgressBar);
        progressBar.setVisibility(View.INVISIBLE);

        password = findViewById(R.id.loginPassword);
        email = findViewById(R.id.loginEmail);
        loginRegisterText = findViewById(R.id.loginRegisterText);
        loginAdditionalInformation = findViewById(R.id.loginAdditionalInformation);
        loginRegisterButton = findViewById(R.id.loginOrRegisterButton);

        loginSignUpSwitch.setOnClickListener(v -> {
            isSignUp = !isSignUp;
            if (isSignUp) {
                fullName.setVisibility(View.VISIBLE);
                userName.setVisibility(View.VISIBLE);
                loginRegisterText.setText("Register");
                loginRegisterButton.setText("Register");
                loginAdditionalInformation.setText("Create an account!");
            } else {
                fullName.setVisibility(View.INVISIBLE);
                userName.setVisibility(View.INVISIBLE);
                loginRegisterText.setText("Login");
                loginRegisterButton.setText("Login");
                loginAdditionalInformation.setText("Please sign in to continue.");
            }
        });

        loginRegisterButton.setOnClickListener(v -> {
            if (isSignUp) {
                // do signup things
                if (validateUsername() && validateEmail() && validatePassword() && validateFullName()) {
                    Toast.makeText(LoginSignUp.this, "Registering this user", Toast.LENGTH_SHORT).show();
                    // post to the database
                    postToDB(userName.getText().toString(), email.getText().toString(),
                            password.getText().toString(), fullName.getText().toString());
                    openDashboardActivity();
                    finish();
                }

            } else {
                if (validateEmail() && validatePassword()) {
                    // if the information is in the database
                    progressBar.setVisibility(View.VISIBLE);
                    checkLoginCredentials(email.getText().toString(), password.getText().toString(),
                            new FirestoreCallback() {
                                @Override
                                public void onCallBack(boolean isValidCredentials) {
                                    if (isValidCredentials) {
                                        Toast.makeText(LoginSignUp.this, "Login Successful", Toast.LENGTH_SHORT)
                                                .show();
                                        openDashboardActivity();
                                    } else {
                                        Toast.makeText(LoginSignUp.this,
                                                "Unable to login. Check credentials.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void checkLoginCredentials(String email, String password,
            FirestoreCallback callback) {

        db.collection("users").get().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    if (Objects.equals(documentSnapshot.get("email"), email)
                            && Objects.equals(documentSnapshot.get("password"), password)) {

                        userName.setText(documentSnapshot.getString("userName"));
                        fullName.setText(documentSnapshot.getString("fullName"));
                        isLoginSuccessful = true;
                        callback.onCallBack(true);
                    }
                }
            }
        });
    }

    private interface FirestoreCallback {
        void onCallBack(boolean isValidCredentials);
    }

    private void postToDB(String userName, String email, String password, String fullName) {
        UserModel user = new UserModel();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        user.setFullName(fullName);

        // TODO: Check if the email is already present in the

        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.i(TAG, "User Successfully pushed");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Unable to create user.");
            }
        });
    }

    /**
     * Validate the email address passed in by the user
     * 
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
     * 
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
     * 
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
        } else if (password.getText().toString().length() < 8) {
            password.setError("password should be at least 8 characters long.");
        }
        return true;
    }

    private void openDashboardActivity() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("userEmail", email.getText().toString());
        intent.putExtra("password", password.getText().toString());
        intent.putExtra("userName", userName.getText().toString());
        intent.putExtra("fullName", fullName.getText().toString());

        startActivity(intent);
    }

}