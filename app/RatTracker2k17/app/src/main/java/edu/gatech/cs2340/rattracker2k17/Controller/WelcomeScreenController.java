package edu.gatech.cs2340.rattracker2k17.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
import edu.gatech.cs2340.rattracker2k17.R;

import static edu.gatech.cs2340.rattracker2k17.R.id.list_view;

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

    public class UsersAdapter extends ArrayAdapter<RatSpotting> {
        public UsersAdapter(Context context, ArrayList<RatSpotting> spots) {
            super(context, 0, spots);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            RatSpotting spot = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
            }

            TextView rsDate = (TextView) convertView.findViewById(R.id.rsDate);
            TextView rsLocationType = (TextView) convertView.findViewById(R.id.rsLocationType);
            TextView rsBorough = (TextView) convertView.findViewById(R.id.rsBorough);

            rsDate.setText(spot.getDate());
            rsLocationType.setText(spot.getLocationType());
            rsBorough.setText(spot.getBorough());

            return convertView;
        }
    }

    public void getRatData(View view) {

        ArrayList<RatSpotting> arrayOfRats = new ArrayList<>();
        Log.d(LOG_ID, "Importing Rat Data...");
//
//
//
//
//        IMPORT RAT DATA HERE
//
//
//
//
//        

        UsersAdapter adapter = new UsersAdapter(this, arrayOfRats);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }


    // change view back home (connect in the "onClick" field in layout)
    public void logout(View view) {
        mAuth.signOut();
        Intent intent = new Intent(this, HomeScreenController.class);
        startActivity(intent);
    }
}

