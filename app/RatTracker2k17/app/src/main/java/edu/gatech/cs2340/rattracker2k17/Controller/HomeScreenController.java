package edu.gatech.cs2340.rattracker2k17.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
import edu.gatech.cs2340.rattracker2k17.R;

/** Controller for home screen
 * @author Justin Z, Will E, Chris O
 * @version 1.0
 */
public class HomeScreenController extends AppCompatActivity {

    private static final String LOG_ID = "HomeScreenController";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        RatSpotting.generateNextKey();
        Log.d(LOG_ID, "onCreate:HomeScreenController");
    }



    /**
     * change to log in screen (connect in the "onClick" field in layout)
     * @param view current view
     */
    public void launchLogIn(View view) {
        Intent intent = new Intent(this, LogInScreenController.class);
        startActivity(intent);
    }

    /**
     * change to log in screen (connect in the "onClick" field in layout)
     * @param view current view
     */
    public void launchNewUser(View view) {
        Intent intent = new Intent(this, NewUserScreenController.class);
        startActivity(intent);
    }

    /**
     * Launch Guest user
     * @param view current view
     */
    public void launchGuestUser(View view) {
        Intent intent = new Intent(this, WelcomeScreenController.class);
        startActivity(intent);
    }
}
