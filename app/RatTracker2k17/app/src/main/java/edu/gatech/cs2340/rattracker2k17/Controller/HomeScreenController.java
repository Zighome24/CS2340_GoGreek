package edu.gatech.cs2340.rattracker2k17.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
import edu.gatech.cs2340.rattracker2k17.R;

public class HomeScreenController extends AppCompatActivity {

    private static final String LOG_ID = "HomeScreenController";


    private Button btn_Login, btn_NewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        RatSpotting.generateNextKey();
        Log.d(LOG_ID, "onCreate:HomeScreenController");
    }

    // change to log in screen (connect in the "onClick" field in layout)
    public void launchLogIn(View view) {
        Intent intent = new Intent(this, LogInScreenController.class);
        startActivity(intent);
    }

    // change to log in screen (connect in the "onClick" field in layout)
    public void launchNewUser(View view) {
        Intent intent = new Intent(this, NewUserScreenController.class);
        startActivity(intent);
    }

    public void launchGuestUser(View view) {
        Intent intent = new Intent(this, WelcomeScreenController.class);
        startActivity(intent);
    }
}
