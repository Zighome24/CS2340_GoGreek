package edu.gatech.cs2340.rattracker2k17.Service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.gatech.cs2340.rattracker2k17.Model.User;


public class LoginBL {

    private final FirebaseAuth mAuth;
    private static final String TAG = "LoginBL";

    public LoginBL() {
        mAuth = FirebaseAuth.getInstance();
    }

    public LoginBL(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public Task<AuthResult> login(String email, String password) {
        Log.d(TAG, "Logging in the user: " + email);
        return mAuth.signInWithEmailAndPassword(email, password);
    }


    @SuppressWarnings("FeatureEnvy")
    public Task<AuthResult> createUser(User user) {
        Log.d(TAG, "Creating a new user: " + user.toString());
        return mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword());
    }

}
