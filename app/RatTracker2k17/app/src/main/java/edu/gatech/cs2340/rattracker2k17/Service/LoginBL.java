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

/**
 * Created by Justin on 10/2/2017.
 */

public class LoginBL {

    private FirebaseAuth mAuth;
    private static final String TAG = "LoginBL";

    public LoginBL() {
        mAuth = FirebaseAuth.getInstance();
    }

    public LoginBL(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public Task<AuthResult> login(String email, String password) throws FirebaseAuthException {
        Log.d(TAG, "Logging in the user: " + email);
        return mAuth.signInWithEmailAndPassword(email, password);
    }

    // TODO: 10/2/2017 Add support for exceptions that could share more information
    //  such as an existing account or bad email.
    public Task<AuthResult> createUser(User user) throws FirebaseAuthException {
        Log.d(TAG, "Creating a new user: " + user.toString());
        return mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword());
    }

}
