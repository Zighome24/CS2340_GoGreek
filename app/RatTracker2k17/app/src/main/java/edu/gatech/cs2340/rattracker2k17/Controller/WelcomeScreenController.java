package edu.gatech.cs2340.rattracker2k17.Controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
import edu.gatech.cs2340.rattracker2k17.R;
import edu.gatech.cs2340.rattracker2k17.Service.RatSpottingBL;
import edu.gatech.cs2340.rattracker2k17.Service.Utility;

/**
 * Controller for welcome screen
 * @author Justin Z
 * @version 1.0
 */

public class WelcomeScreenController extends AppCompatActivity {

    private static final String LOG_ID = "WelcomeScreenController";
    private FirebaseAuth mAuth;
    private RatSpottingAdapter ratAdapter;
    private ListView listView;
    private RatSpottingBL ratSpottingBL;

    private static String TAG = WelcomeScreenController.class.getSimpleName();
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescreen);

        listView = findViewById(R.id.list_view);
        ratAdapter = new RatSpottingAdapter(this, new ArrayList<>());

        ratSpottingBL = new RatSpottingBL();

        mAuth = FirebaseAuth.getInstance();
        getRatData();

        mNavItems.add(new NavItem("Map", "View map view of spottings", R.drawable.ic_media_play_light));
        mNavItems.add(new NavItem("Graph", "View graph of spottings", R.drawable.ic_media_play_light));
        mNavItems.add(new NavItem("Logout", "", R.drawable.ic_media_play_light));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });


        Log.d(LOG_ID, "WelcomeScreenController:onCreate: welcome screen created");
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(ratAdapter);
    }

    /////////////////////////////

    private class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    private class DrawerListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            }
            else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText( mNavItems.get(position).mTitle );
            subtitleView.setText( mNavItems.get(position).mSubtitle );
            iconView.setImageResource(mNavItems.get(position).mIcon);

            return view;
        }
    }

    private void selectItemFromDrawer(int position) {

        switch (position) {
            case 0:
                Intent intent1 = new Intent(this, DateSelectionScreenController.class);
                startActivity(intent1);
                break;
            case 1:
                Intent intent2 = new Intent(this, DateSelectionScreenController.class);
                startActivity(intent2);
                break;
            case 2:
                mAuth.signOut();
                Intent intent = new Intent(this, HomeScreenController.class);
                startActivity(intent);
                RatSpottingBL.pushCurrentKey();
                finish();
                break;
        }



        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //////////////////////////

    @SuppressWarnings("FeatureEnvy")
    private class RatSpottingAdapter extends ArrayAdapter<RatSpotting> {
        private RatSpottingAdapter(Context context, List<RatSpotting> spots) {
            super(context, 0, spots);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final RatSpotting spot = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_rat,
                        parent, false);
                convertView.setOnClickListener(v -> {
                    Log.d(LOG_ID, "Clicking on a certain rat spotting: " + spot.toString());
                    Intent intent = new Intent(WelcomeScreenController.this,
                            DetailRatScreenController.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("spotting", spot);
                    intent.putExtras(bundle);
                    startActivity(intent);
                });
            }

            TextView rsDate = convertView.findViewById(R.id.rsDate);
            TextView rsLocationType = convertView.findViewById(R.id.rsLocationType);
            TextView rsBorough = convertView.findViewById(R.id.rsBorough);

            rsDate.setText("Key: " + spot.getKey() + "    ");
            rsLocationType.setText("Zip: " + Long.toString(spot.getZip()) + "    ");
            rsBorough.setText("Borough: " + spot.getBorough());

            return convertView;
        }
    }

    /**
     * getRatData - queries the server for rat data and places it on the screen once the request
     * is returned
     */
    public void getRatData() {
        Log.d(LOG_ID, "Importing Rat Data...");
        ratSpottingBL.getRecentRatSpottings().addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(LOG_ID, dataSnapshot.toString());
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Log.d(LOG_ID, data.toString());
                            RatSpotting rat = Utility.getRatSpottingFromSnapshot(data);
                            Log.d(LOG_ID, rat.toString());
                            ratAdapter.add(rat);
                        }
                        listView.setAdapter(ratAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(LOG_ID, databaseError.getMessage());
                    }
                }
        );
    }

    /**
     * logout - logs the user out and returns to the homescreen
     * @param view - the view object that is calling the logout method()
     */
    public void logout(View view) {
        mAuth.signOut();
        Intent intent = new Intent(this, HomeScreenController.class);
        startActivity(intent);
        RatSpottingBL.pushCurrentKey();
        finish();
    }

    /**
     * newRatSpotting - changes the activity to the rat-spotting creation screen
     * @param view - the view object that is calling the newRatSpotting() method
     */
    public void newRatSpotting(View view) {
        Intent intent = new Intent(this, NewRatSpottingController.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_ID, "Returned to Welcome Screen Controller");
        if (data != null && data.getExtras() != null) {
            RatSpotting rat = (RatSpotting) data.getExtras().getSerializable("ratspotting");
            if (rat != null) {
                Log.d(LOG_ID, "NewRatSpottingController has returned RatSpotting: "
                        + rat.getKey() + " toString(): " + rat.toString());
                ratAdapter.add(rat);
            } else {
                Log.d(LOG_ID, "NewRatSpottingController has returned a null RatSpotting");
            }

        }
    }

    /**
     * viewRatSpottings
     * @param view current view
     */
    public void viewRatSpottings(View view) {
        Intent intent = new Intent(this, DateSelectionScreenController.class);
        startActivity(intent);
    }
}

