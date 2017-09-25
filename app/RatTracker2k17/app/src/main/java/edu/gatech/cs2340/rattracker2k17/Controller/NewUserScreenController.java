package edu.gatech.cs2340.rattracker2k17.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.gatech.cs2340.rattracker2k17.R;

/**
 * Created by wepperson on 9/24/17.
 */

public class NewUserScreenController extends AppCompatActivity {

    private static final String LOG_ID = "NewUserScreenController";

    private Button btn_createUser, btn_returnHome;
    private EditText firstName, lastName, email, username, password;

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
        username = (EditText) findViewById(R.id.txt_username);
        password = (EditText) findViewById(R.id.txt_password);

        Log.d(LOG_ID, firstName.getText().toString() + lastName.getText().toString());

    }
}
