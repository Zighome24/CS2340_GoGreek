package edu.gatech.cs2340.rattracker2k17.Service;

import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import edu.gatech.cs2340.rattracker2k17.Model.User;

/** Controls logic for login
 * @author Justin Z
 * @version 1.0
 */
public class LoginBL {

    private final FirebaseAuth mAuth;
    private static final String TAG = "LoginBL";

    /**
     * Constructor: gets firebase instance
     */
    public LoginBL() {
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Constructor with mauth
     * @param mAuth take in a firebase auth
     */
    public LoginBL(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    /**
     * Log in the user on firebase
     * @param email user email
     * @param password user password
     * @return result of login
     */
    public Task<AuthResult> login(String email, String password) {
        Log.d(TAG, "Logging in the user: " + email);
        return mAuth.signInWithEmailAndPassword(email, password);
    }

    /**
     *  Create user
     * @param user the user to be created
     * @return result of log in
     */
    @SuppressWarnings("FeatureEnvy")
    public Task<AuthResult> createUser(User user) {
        Log.d(TAG, "Creating a new user: " + user.toString());
        return mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword());
    }

}
