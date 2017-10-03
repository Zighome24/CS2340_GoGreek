package edu.gatech.cs2340.rattracker2k17.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import edu.gatech.cs2340.rattracker2k17.Data.Types;
import edu.gatech.cs2340.rattracker2k17.Model.User;
import edu.gatech.cs2340.rattracker2k17.R;
import edu.gatech.cs2340.rattracker2k17.Service.LoginBL;
import edu.gatech.cs2340.rattracker2k17.Service.UserBL;

/**
 * Created by wepperson on 9/24/17.
 */

public class NewUserScreenController extends AppCompatActivity {

    private static final String LOG_ID = "NewUserScreenController";

    private EditText firstName, lastName, email,password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);

        Log.d(LOG_ID, "NewUserScreenController:onCreate: new user screen created");

        mAuth = FirebaseAuth.getInstance();
    }

    // change view back home (connect in the "onClick" field in layout)
    public void back(View view) {
        Intent intent = new Intent(this, HomeScreenController.class);
        startActivity(intent);
        finish();
    }

    // creates a new User (connect in the "onClick" field in layout)
    public void createUser(View view) {

        EditText firstName = findViewById(R.id.txt_firstName);
        EditText lastName = findViewById(R.id.txt_lastName);
        EditText email = findViewById(R.id.txt_email);
        EditText password = findViewById(R.id.txt_password);


        final User nUser = new User(firstName.getText().toString(), lastName.getText().toString(),
                                email.getText().toString(), Types.SecurityLevel.User, User.NO_UID,
                                password.getText().toString());

        Log.d(LOG_ID, "createUser: " + nUser.toString());

        final UserBL userBL = new UserBL();

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null
                        && firebaseAuth.getCurrentUser().getEmail() != null
                        && firebaseAuth.getCurrentUser().getEmail().equals(nUser.getEmail())) {
                    nUser.setUserID(firebaseAuth.getCurrentUser().getUid());
                    try {
                        userBL.addNewUser(nUser);
                    } catch (Exception e) {
                        Log.d(LOG_ID, e.getMessage());
                    }
                    mAuth.removeAuthStateListener(this);
                    Intent intent = new Intent(NewUserScreenController.this,
                            WelcomeScreenController.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(NewUserScreenController.this,
                            "The user could not be created. ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        LoginBL loginBL = new LoginBL(mAuth);
        try {
            loginBL.createUser(nUser)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(LOG_ID, "createUser:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            Log.d(LOG_ID, "New User Log: " + task.getResult().getUser().toString());
                        } else {
                            Log.d(LOG_ID, "Could not create new user: "
                                    + task.getResult().getUser().toString());
                        }
                    }
                });
        } catch (FirebaseAuthUserCollisionException eUser) {
            switch (eUser.getErrorCode()) {
                case "ERROR_EMAIL_ALREADY_IN_USE":
                    AlertDialog.Builder dialogueBuilderD = new AlertDialog.Builder(this);
                    dialogueBuilderD.setMessage("The email " + nUser.getEmail()
                            + " is already associated with an account."
                            + " Would you like to be redirected to the login screen")
                            .setTitle("Error");

                    dialogueBuilderD.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(NewUserScreenController.this,
                                    LogInScreenController.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    dialogueBuilderD.setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialogD = dialogueBuilderD.create();
                    dialogD.show();
                    break;
                default:
                    Toast.makeText(this, R.string.system_error, Toast.LENGTH_LONG).show();
                    Log.d(LOG_ID, "Unexpected Error Code: " + eUser.getErrorCode()
                            + " with message: " + eUser.getMessage());
            }
        } catch (FirebaseAuthInvalidCredentialsException eCred) {
            Toast.makeText(this, eCred.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (FirebaseAuthException eAuth) {
            Log.d(LOG_ID, "Unexpected error message: " + eAuth.getMessage()
                    + ", Error code: " + eAuth.getErrorCode());
        }
    }
}
