package edu.gatech.cs2340.rattracker2k17.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Arrays;

import edu.gatech.cs2340.rattracker2k17.Data.Types;
import edu.gatech.cs2340.rattracker2k17.Model.User;
import edu.gatech.cs2340.rattracker2k17.R;
import edu.gatech.cs2340.rattracker2k17.Service.LoginBL;
import edu.gatech.cs2340.rattracker2k17.Service.UserBL;
import edu.gatech.cs2340.rattracker2k17.Service.Utility;


@SuppressWarnings("FeatureEnvy")
public class NewUserScreenController extends AppCompatActivity {

    private static final String LOG_ID = "NewUserScreenController";

    private Spinner spinner;
    private FirebaseAuth mAuth;
    private EditText firstName,lastName, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);

        Log.d(LOG_ID, "NewUserScreenController:onCreate: new user screen created");

        firstName = findViewById(R.id.txt_NewUserFirstName);
        lastName = findViewById(R.id.txt_NewUserLastName);
        email = findViewById(R.id.txt_NewUserEmail);
        password = findViewById(R.id.txt_NewUserPassword);

        if (getIntent().getExtras() != null) {
            Log.d(LOG_ID, getIntent().getExtras().getString("email"));
            email.setText(getIntent().getExtras().getString("email"), TextView.BufferType.EDITABLE);
        }

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
        final User nUser = new User(firstName.getText().toString(), lastName.getText().toString(),
                                email.getText().toString(), Types.SecurityLevel.User, User.NO_UID,
                                password.getText().toString());

        if (!ValidateForm(nUser)) {
            Toast.makeText(this, "Not all of the fields listed above are filled out, please "
                    + "fill them all out before you create a new user.", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(LOG_ID, "createUser: " + nUser.toString());

        final UserBL userBL = new UserBL();

        LoginBL loginBL = new LoginBL(mAuth);
        //noinspection FeatureEnvy
        loginBL.createUser(nUser).addOnCompleteListener(task -> {
            Log.d(LOG_ID, "createUser:onComplete:" + task.isSuccessful());
            if (task.isSuccessful()) {
                Log.d(LOG_ID, "New User Log: " + task.getResult().getUser().toString());
                try {
                    nUser.setUserID(task.getResult().getUser().getUid());
                    userBL.addNewUser(nUser);
                } catch (Exception e) {
                    Log.d(LOG_ID, e.getMessage());
                }
                Intent intent = new Intent(NewUserScreenController.this,
                        WelcomeScreenController.class);
                startActivity(intent);
                finish();
            } else {
                try {
                    throw task.getException();
                } catch (FirebaseAuthUserCollisionException eUser) {
                    switch (eUser.getErrorCode()) {
                        case "ERROR_EMAIL_ALREADY_IN_USE":
                            AlertDialog.Builder dialogueBuilderD =
                                    new AlertDialog.Builder(NewUserScreenController.this);
                            dialogueBuilderD.setMessage("The email " + nUser.getEmail()
                                    + " is already associated with an account."
                                    + " Would you like to be redirected to the login screen")
                                    .setTitle("Error");

                            dialogueBuilderD.setPositiveButton("OK", (dialog, id) -> {
                                Intent intent = new Intent(NewUserScreenController.this,
                                        LogInScreenController.class);
                                intent.putExtra("email", nUser.getEmail());
                                startActivity(intent);
                                finish();
                            });
                            dialogueBuilderD.setNegativeButton("No Thanks", (dialog, id)
                                    -> dialog.dismiss());
                            AlertDialog dialogD = dialogueBuilderD.create();
                            dialogD.show();
                            break;
                        default:
                            Toast.makeText(NewUserScreenController.this, R.string.system_error,
                                    Toast.LENGTH_LONG).show();
                            Log.d(LOG_ID, "Unexpected Error Code: " + eUser.getErrorCode()
                                    + " with message: " + eUser.getMessage());
                    }
                } catch (FirebaseAuthInvalidCredentialsException eCred) {
                    Toast.makeText(NewUserScreenController.this,
                            eCred.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (FirebaseAuthException eAuth) {
                    Log.d(LOG_ID, "Unexpected Firebase Auth error, message: "
                            + eAuth.getMessage() + ", Error code: " + eAuth.getErrorCode());
                } catch (Exception e) {
                    Log.d(LOG_ID, "Unexpected Error, message: " + e.getMessage());
                }
            }
        });
    }

    /**
     * Validate Form:
     * @param user - the user object containing the information to validate
     * @return - returns true if the form has no empty fields and false if it does not.
     */
    private boolean ValidateForm(User user) {
        return !(Utility.isNullOrWhitespace(user.getFirstName())
                | Utility.isNullOrWhitespace(user.getLastName())
                | Utility.isNullOrWhitespace(user.getEmail())
                | Utility.isNullOrWhitespace(user.getPassword()));
    }
}
