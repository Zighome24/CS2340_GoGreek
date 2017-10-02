package edu.gatech.cs2340.rattracker2k17.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import edu.gatech.cs2340.rattracker2k17.Data.Types;
import edu.gatech.cs2340.rattracker2k17.Model.User;
import edu.gatech.cs2340.rattracker2k17.R;
import edu.gatech.cs2340.rattracker2k17.Service.UserBL;

/**
 * Created by wepperson on 9/24/17.
 */

public class NewUserScreenController extends AppCompatActivity {

    private static final String LOG_ID = "NewUserScreenController";

    private Button btn_createUser, btn_returnHome;
    private EditText firstName, lastName, email, username, password, cPassword;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
    }

    // change view back home (connect in the "onClick" field in layout)
    public void back(View view) {
        Intent intent = new Intent(this, HomeScreenController.class);
        startActivity(intent);
    }

    // change view back home (connect in the "onClick" field in layout)
    public void createUser(View view) {
        //Intent intent = new Intent(this, HomeScreenController.class);
        //startActivity(intent);

        firstName = (EditText) findViewById(R.id.txt_firstName);
        lastName = (EditText) findViewById(R.id.txt_lastName);
        email = (EditText) findViewById(R.id.txt_email);
        password = (EditText) findViewById(R.id.txt_password);


        final User nUser = new User(firstName.getText().toString(), lastName.getText().toString(),
                                email.getText().toString(), Types.SecurityLevel.User, User.NO_UID,
                                password.getText().toString());

        Log.d(LOG_ID, "createUser: " + nUser.toString());

        final UserBL userBL = new UserBL();

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    nUser.setUserID(firebaseAuth.getCurrentUser().getUid());
                    try {
                        userBL.addNewUser(nUser);
                    } catch (Exception e) {
                        Log.d(LOG_ID, e.getMessage());
                    }
                } else {
                    Toast.makeText(NewUserScreenController.this,
                            "The user could not be created. "
                            + "Please check to see if that email already owns an account.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
