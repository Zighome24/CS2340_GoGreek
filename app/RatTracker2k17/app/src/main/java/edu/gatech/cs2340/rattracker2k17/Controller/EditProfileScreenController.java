package edu.gatech.cs2340.rattracker2k17.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.cs2340.rattracker2k17.Model.User;
import edu.gatech.cs2340.rattracker2k17.R;

import edu.gatech.cs2340.rattracker2k17.R;
import edu.gatech.cs2340.rattracker2k17.Service.UserBL;
import edu.gatech.cs2340.rattracker2k17.Service.Utility;

/**
 * Created by Chris on 12/5/17.
 */

public class EditProfileScreenController extends AppCompatActivity {

    private EditText firstName,lastName, email, password;
    private String strFirstName,strLastName, strEmail, strPassword;
    private FirebaseAuth mAuth;
    private static final String LOG_ID = "EditProfileController";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        Log.d(LOG_ID, "EditProfileScreenController:onCreate: edit profile screen created");

        firstName = findViewById(R.id.txt_EditFirstName);
        lastName = findViewById(R.id.txt_EditLastName);
        email = findViewById(R.id.txt_EditEmail);
        password = findViewById(R.id.txt_EditPassword);

        if (getIntent().getExtras() != null) {
            User user = (User) getIntent().getExtras().getSerializable("user");
            if (user != null) {
                firstName.setText(user.getFirstName());
                lastName.setText(user.getLastName());
                email.setText(user.getEmail());
                password.setText(user.getPassword());
                strFirstName = user.getFirstName();
                strLastName = user.getLastName();
                strEmail = user.getEmail();
                strPassword = user.getEmail();
            } else {
                Log.d(LOG_ID, "The User was null.");
            }
        } else {
            Log.d(LOG_ID, "The calling Intent did not pass in any extras");
        }

        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Change view back home (connect in the "onClick" field in layout)
     * @param view current view
     */
    public void back(View view) {
        Intent intent = new Intent(this, WelcomeScreenController.class);
        startActivity(intent);
        finish();
    }

    /**
     * Edits current User (connect in the "onClick" field in layout)
     * @param view current view
     */
    public void save(View view) {

        if (firstName != null || !strFirstName.equals(firstName)) {
            strFirstName = firstName.getText().toString();
        }
        if (lastName != null || !strLastName.equals(lastName)) {
            strLastName = lastName.getText().toString();
        }
        if (email != null || !strEmail.equals(email)) {
            strEmail = email.getText().toString();
        }
        if (password != null || !strPassword.equals(password)) {
            strPassword = password.getText().toString();
        }
        UserBL userBL = new UserBL();
        userBL.getUser(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = Utility.getUserFromSnapshot(dataSnapshot);
                user.setFirstName(strFirstName);
                user.setLastName(strLastName);
                user.setEmail(strEmail);
                user.setPassword(strPassword);
                DatabaseReference ref = dataSnapshot.getRef();
                Log.d(LOG_ID, "Ref: " + ref.toString());
                ref.child("firstName").setValue(strFirstName);
                ref.child("lastName").setValue(strLastName);
                ref.child("email").setValue(strEmail);
                ref.child("password").setValue(strPassword);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                Intent intent = new Intent(EditProfileScreenController.this,
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


    }


}
