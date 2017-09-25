package edu.gatech.cs2340.rattracker2k17.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.gatech.cs2340.rattracker2k17.R;

/**
 * Created by wepperson on 9/24/17.
 */

public class LogInScreenController extends AppCompatActivity {

    private static final String LOG_ID = "LogInScreenController";


    private Button btn_login, btn_return;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);

    }

    // log in
    public void login(View view) {

        username = (EditText) findViewById(R.id.txt_userName);
        password = (EditText) findViewById(R.id.txt_password);

        if (username.getText().toString().equals("user") && password.getText().toString().equals("pass")) {
            System.out.println("WE IN HERRRRE");
            Intent intent = new Intent(this, WelcomeScreenController.class);
            startActivity(intent);
        } else {
            System.out.println("Nah.");
            badLogOn();

        }

    }

    // change view back home (connect in the "onClick" field in layout)
    public void backHome(View view) {
        Intent intent = new Intent(this, HomeScreenController.class);
        startActivity(intent);
    }

    /**
     * Button handler
     */
    public void badLogOn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Invalid username / password").setTitle("Error");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
