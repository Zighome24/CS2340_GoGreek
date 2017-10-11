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
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
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

    public class UsersAdapter extends ArrayAdapter<RatSpotting> {
        public UsersAdapter(Context context, ArrayList<RatSpotting> spots) {
            super(context, 0, spots);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final RatSpotting spot = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_rat, parent, false);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(LOG_ID, "Clicking on a certain rat spotting: " + spot.toString());
                        Intent intent = new Intent(WelcomeScreenController.this, DetailRatScreenController.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("spotting", spot);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

            TextView rsDate = (TextView) convertView.findViewById(R.id.rsDate);
            TextView rsLocationType = (TextView) convertView.findViewById(R.id.rsLocationType);
            TextView rsBorough = (TextView) convertView.findViewById(R.id.rsBorough);

            rsDate.setText("Key: " + Integer.toString(spot.getKey()) + "       ");
            rsLocationType.setText("Zip: " + Integer.toString(spot.getZip()) + "       ");
            rsBorough.setText("Borough: " + spot.getBorough());

            return convertView;
        }
    }

    public void getRatData(View view) {

        ArrayList<RatSpotting> arrayOfRats = new ArrayList<>();
        Log.d(LOG_ID, "Importing Rat Data...");
        try {
            InputStream is = getResources().openRawResource(R.raw.ratsightings);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            int counter = 0;
            String line;
            br.readLine(); //get rid of header line
            while ((line = br.readLine()) != null && counter < 50) {
                counter++;
                Log.d(LOG_ID, line);
                String[] tokens = line.split(",");
                arrayOfRats.add(new RatSpotting(
                    Integer.parseInt(tokens[0]),
                    tokens[1],
                    tokens[7],
                    tokens[8].isEmpty() ? 0 : Integer.parseInt(tokens[8]),
                    tokens[9],
                    tokens[16],
                    tokens[23],
                    tokens.length >= 49 ? Double.parseDouble(tokens[49]) : 33.777292,
                    tokens.length >= 50 ? Double.parseDouble(tokens[50]) : -84.398103));
                // TODO: 10/11/2017 Make sure the google maps zoom feature doesn't get screwed by rats in howey
            }
            br.close();
        } catch (IOException e) {
            Log.e(LOG_ID, "error reading assets", e);
        }

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

