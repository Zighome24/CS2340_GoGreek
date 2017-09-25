package edu.gatech.cs2340.rattracker2k17.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.gatech.cs2340.rattracker2k17.R;

/**
 * Created by wepperson on 9/24/17.
 */

public class WelcomeScreenController extends AppCompatActivity {

    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescreen);


        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeScreenController.class);
                startActivity(intent);
                finish();
            }
        });
    }


    // change view back home (connect in the "onClick" field in layout)
    public void logout(View view) {
        Intent intent = new Intent(this, HomeScreenController.class);
        startActivity(intent);
    }
}

