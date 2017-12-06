package edu.gatech.cs2340.rattracker2k17.Controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.rattracker2k17.Data.Types;
import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
import edu.gatech.cs2340.rattracker2k17.Model.User;
import edu.gatech.cs2340.rattracker2k17.Model.UserLogReport;
import edu.gatech.cs2340.rattracker2k17.R;
import edu.gatech.cs2340.rattracker2k17.Service.LogReportBL;
import edu.gatech.cs2340.rattracker2k17.Service.LoginBL;
import edu.gatech.cs2340.rattracker2k17.Service.UserBL;
import edu.gatech.cs2340.rattracker2k17.Service.Utility;

/** Controller for log in
 * @author Will E
 * @version 1.0
 */
public class LogInScreenController extends AppCompatActivity {

    private static final String LOG_ID = "LogInScreenController";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);

        mAuth = FirebaseAuth.getInstance();

        if (getIntent().getExtras() != null) {
            EditText txt_email = findViewById(R.id.txt_LoginEmail);
            txt_email.setText(getIntent().getExtras().getString("email"),
                    TextView.BufferType.EDITABLE);
        }

        Log.d(LOG_ID, "LogInScreenController:onCreate: login screen created");
    }

    /**
     * Do log in
     * @param view the current view
     */
    public void login(View view) {

        EditText email = findViewById(R.id.txt_LoginEmail);
        EditText password = findViewById(R.id.txt_LoginPassword);

        final String str_email = email.getText().toString();

        if (!validateForm(str_email, password.getText().toString())) {
            Toast.makeText(this, "Not all of the fields listed above are filled out, please "
                    + "fill them all out before you login.", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginBL loginBL = new LoginBL(mAuth);
        loginBL.login(str_email, password.getText().toString())
                .addOnCompleteListener(this, task -> {
                    Log.d(LOG_ID, "loginUser:onComplete:" + task.isSuccessful());
                    if (task.isSuccessful()) {
                        Log.d(LOG_ID, "User Logged in: " + task.getResult().getUser().toString());
                        LogReportBL reportBL = new LogReportBL();
                        reportBL.pushReport(new UserLogReport(Types.Logging.Login, task.getResult().getUser()));
                        RatSpotting.generateNextKey();

                        UserBL userBL = new UserBL();
                        userBL.getUser(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User user = Utility.getUserFromSnapshot(dataSnapshot);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user", user);

                                Intent intent = new Intent(LogInScreenController.this,
                                        WelcomeScreenController.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d(LOG_ID, "The request for login() has been canceled, "
                                        + "message: " + databaseError.getDetails());
                            }
                        });


                    } else {
                        try {
                            // Fire API will never throw a null
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException eUser) {
                            switch (eUser.getErrorCode()) {
                                case "ERROR_USER_DISABLED":
                                    AlertDialog.Builder dialogueBuilderD = new AlertDialog.Builder(
                                            LogInScreenController.this);
                                    dialogueBuilderD.setMessage("The account associated with the " +
                                            "email " + str_email + " is disabled. Would you like " +
                                            "to be redirected to the user creation screen")
                                            .setTitle("Error");

                                    dialogueBuilderD.setPositiveButton("OK", (dialog, id) -> {
                                        Intent intent = new Intent(LogInScreenController.this,
                                                NewUserScreenController.class);
                                        startActivity(intent);
                                        finish();
                                    });
                                    dialogueBuilderD.setNegativeButton("No Thanks",
                                            (dialog, id) -> dialog.dismiss());
                                    AlertDialog dialogD = dialogueBuilderD.create();
                                    dialogD.show();
                                    break;
                                case "ERROR_USER_NOT_FOUND":
                                    AlertDialog.Builder dialogueBuilderNF = new AlertDialog.Builder(
                                            LogInScreenController.this);
                                    dialogueBuilderNF.setMessage("The email " + str_email
                                            + " does not have an associated account."
                                            + " Would you like to be redirected to the user " +
                                            "creation screen").setTitle("Error");

                                    dialogueBuilderNF.setPositiveButton("OK", (dialog, id) -> {
                                        Intent intent = new Intent(LogInScreenController.this,
                                                NewUserScreenController.class);
                                        intent.putExtra("email", str_email);
                                        startActivity(intent);
                                        finish();
                                    });
                                    dialogueBuilderNF.setNegativeButton("No Thanks",
                                            (dialog, id) -> dialog.dismiss());
                                    AlertDialog dialog_NF = dialogueBuilderNF.create();
                                    dialog_NF.show();
                                    break;
                                default:
                                    Toast.makeText(LogInScreenController.this,
                                            R.string.system_error, Toast.LENGTH_LONG).show();
                                    Log.d(LOG_ID, "Unexpected Error Code: " + eUser.getErrorCode()
                                            + " with message: " + eUser.getMessage());
                            }
                        } catch (FirebaseAuthInvalidCredentialsException eCred) {
                            Toast.makeText(LogInScreenController.this, eCred.getMessage(),
                                    Toast.LENGTH_SHORT).show();
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
     * Change view back home (connect in the "onClick" field in layout)
     * @param view current view
     */
    public void backHome(View view) {
        Intent intent = new Intent(this, HomeScreenController.class);
        startActivity(intent);
        finish();
    }

    /**
     * ValidateForm - validates whether the provided email and password are valid
     * @param email the given email
     * @param password the given password
     * @return true if the form is valid, false if the form is invalid
     */
    private boolean validateForm(String email, String password) {
        return !(Utility.isNullOrWhitespace(email)
                | Utility.isNullOrWhitespace(password));
    }
}
