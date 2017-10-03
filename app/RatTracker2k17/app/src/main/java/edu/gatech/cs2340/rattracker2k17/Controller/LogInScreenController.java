package edu.gatech.cs2340.rattracker2k17.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import edu.gatech.cs2340.rattracker2k17.R;
import edu.gatech.cs2340.rattracker2k17.Service.LoginBL;

/**
 * Created by wepperson on 9/24/17.
 */

public class LogInScreenController extends AppCompatActivity {

    private static final String LOG_ID = "LogInScreenController";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);

        if (getIntent().getExtras() != null) {
            EditText txt_email = findViewById(R.id.txt_LoginEmail);
            txt_email.setText(getIntent().getExtras().getString("email"), TextView.BufferType.EDITABLE);
        }

        Log.d(LOG_ID, "LogInScreenController:onCreate: login screen created");

        mAuth = FirebaseAuth.getInstance();
    }

    // log in
    public void login(View view) {

        EditText email = (EditText) findViewById(R.id.txt_LoginEmail);
        EditText password = (EditText) findViewById(R.id.txt_LoginPassword);

        final String str_email = email.getText().toString();

        LoginBL loginBL = new LoginBL(mAuth);
        loginBL.login(str_email, password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(LOG_ID, "createUser:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            Log.d(LOG_ID, "User Logged in: " + task.getResult().getUser().toString());
                            Intent intent = new Intent(LogInScreenController.this,
                                    WelcomeScreenController.class);
                            startActivity(intent);
                            finish();
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException eUser) {
                                switch (eUser.getErrorCode()) {
                                    case "ERROR_USER_DISABLED":
                                        AlertDialog.Builder dialogueBuilderD = new AlertDialog.Builder(LogInScreenController.this);
                                        dialogueBuilderD.setMessage("The account associated with the email " + str_email + " is disabled."
                                                + " Would you like to be redirected to the user creation screen")
                                                .setTitle("Error");

                                        dialogueBuilderD.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent intent = new Intent(LogInScreenController.this,
                                                        NewUserScreenController.class);
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
                                    case "ERROR_USER_NOT_FOUND":
                                        AlertDialog.Builder dialogueBuilderNF = new AlertDialog.Builder(LogInScreenController.this);
                                        dialogueBuilderNF.setMessage("The email " + str_email
                                                + " does not have an associated account."
                                                + " Would you like to be redirected to the user creation screen")
                                                .setTitle("Error");

                                        dialogueBuilderNF.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent intent = new Intent(LogInScreenController.this,
                                                        NewUserScreenController.class);
                                                intent.putExtra("email", str_email);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                        dialogueBuilderNF.setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        });
                                        AlertDialog dialog_NF = dialogueBuilderNF.create();
                                        dialog_NF.show();
                                        break;
                                    default:
                                        Toast.makeText(LogInScreenController.this, R.string.system_error, Toast.LENGTH_LONG).show();
                                        Log.d(LOG_ID, "Unexpected Error Code: " + eUser.getErrorCode()
                                                + " with message: " + eUser.getMessage());
                                }
                            } catch (FirebaseAuthInvalidCredentialsException eCred) {
                                Toast.makeText(LogInScreenController.this, eCred.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthException eAuth) {
                                Log.d(LOG_ID, "Unexpected Firebase Auth error, message: "
                                        + eAuth.getMessage() + ", Error code: " + eAuth.getErrorCode());
                            } catch (Exception e) {
                                Log.d(LOG_ID, "Unexpected Error, message: " + e.getMessage());
                            }
                        }
                    }
                });
    }

    // change view back home (connect in the "onClick" field in layout)
    public void backHome(View view) {
        Intent intent = new Intent(this, HomeScreenController.class);
        startActivity(intent);
        finish();
    }

    /**
     * We will utilize this later on to count the number of tries and do requests
     * like reset your password or other requests
     */
    public void badLogOn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Invalid username / password").setTitle("Error");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
