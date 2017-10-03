package edu.gatech.cs2340.rattracker2k17.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import edu.gatech.cs2340.rattracker2k17.R;

/**
 * Created by wepperson on 9/24/17.
 */

public class WelcomeScreenController extends AppCompatActivity {

    private static final String LOG_ID = "WelcomeScreenController";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescreen);

        mAuth = FirebaseAuth.getInstance();

        Log.d(LOG_ID, "WelcomeScreenController:onCreate: welcome screen created");
    }


    // change view back home (connect in the "onClick" field in layout)
    public void logout(View view) {
        mAuth.signOut();
        Intent intent = new Intent(this, HomeScreenController.class);
        startActivity(intent);
    }
}

