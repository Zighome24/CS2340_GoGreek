package edu.gatech.cs2340.rattracker2k17.Service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.gatech.cs2340.rattracker2k17.Model.User;

/**
 * Created by Justin on 10/2/2017.
 */

public class LoginBL {

    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private static final String TAG = "LoginBL";

    public LoginBL() {
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();
    }

    public LoginBL(DatabaseReference mDataBase, FirebaseAuth mAuth) {
        if (mAuth != null) {
            this.mAuth = mAuth;
        } else {
            this.mAuth = FirebaseAuth.getInstance();
        }
        if (mDataBase != null) {
            this.mDataBase = mDataBase;
        } else {
            this.mDataBase = FirebaseDatabase.getInstance().getReference();
        }
    }

    public void login(User user) {
        Log.d(TAG, "Logging in the user: " + user.toString());
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "login:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User Log: " + task.getResult().getUser().toString());
                        } else {
                            Log.d(TAG, "Login failed for User: "
                                    + task.getResult().getUser().toString());
                        }
                    }
                });
    }

    // TODO: 10/2/2017 Add support for exceptions that could share more information
    //  such as an existing account or bad email.
    public void createUser(User user) {
        Log.d(TAG, "Creating a new user: " + user.toString());
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            Log.d(TAG, "New User Log: " + task.getResult().getUser().toString());
                        } else {
                            Log.d(TAG, "Could not create new user: "
                                    + task.getResult().getUser().toString());
                        }
                    }
                });
    }

}
