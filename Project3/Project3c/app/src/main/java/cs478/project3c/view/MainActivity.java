package cs478.project3c.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cs478.project3c.R;
import cs478.project3c.model.PhoneDatabase;

public class MainActivity extends AppCompatActivity implements PhoneListFragment.OnPhoneListItemSelectedListener {

    private final static String SHOW_WEBSITE_ACTION = "edu.uic.cs478.f19.showPhoneWebsite";
    private final static String KABOOM_PERMISSION = "edu.uic.cs478.f19.kaboom";

    private PhoneListFragment phoneListFragment;
    private PhoneImageFragment phoneImageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Retrieve or create fragments
        FragmentManager fm = getSupportFragmentManager();

        phoneListFragment = (PhoneListFragment) fm.findFragmentByTag("phoneListFragment");
        if (phoneListFragment == null) {
            phoneListFragment = new PhoneListFragment();
            fm.beginTransaction()
                    .replace(R.id.fragmentContainer1, phoneListFragment, "phoneListFragment")
                    .commit();
        }

        phoneImageFragment = (PhoneImageFragment) fm.findFragmentByTag("phoneImageFragment");
        if (phoneImageFragment == null) {
            phoneImageFragment = new PhoneImageFragment();
        }

        fm.addOnBackStackChangedListener(this::adjustFragmentLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.option_open_AB) {
            // Check that an item has been selected
            if (phoneListFragment.getSelectedItem() == null)
                Toast.makeText(this, "Select a phone first!", Toast.LENGTH_SHORT).show();
            else {
                // Send a broadcast intent
                Intent intent = new Intent();
                intent.setAction(SHOW_WEBSITE_ACTION);
                intent.putExtra("phoneUrl", phoneListFragment.getSelectedItem().getUrl());
                sendOrderedBroadcast(intent, KABOOM_PERMISSION);
            }
        } else if (item.getItemId() == R.id.option_close_app) {
            finish();
        }

        return true;
    }

    @Override
    public void onPhoneListItemSelected(int selected) {
        // Add image fragment if not added
        if (!phoneImageFragment.isAdded()) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction t = fm.beginTransaction();
            t.add(R.id.fragmentContainer2, phoneImageFragment, "phoneImageFragment");
            t.addToBackStack(null);
            t.commit();
        }

        // Set the phone image and adjust the layout
        phoneImageFragment.setPhoneImage(PhoneDatabase.ALL_PHONES.get(selected).getPictureResource());
        adjustFragmentLayout();
    }

    public void adjustFragmentLayout() {
        // Get fragment containers
        FrameLayout fragmentContainer1 = findViewById(R.id.fragmentContainer1);
        FrameLayout fragmentContainer2 = findViewById(R.id.fragmentContainer2);

        if (phoneImageFragment.isAdded()) {
            // Show the corresponding FrameLayout and hide the other if in portrait
            fragmentContainer2.setVisibility(View.VISIBLE);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                fragmentContainer1.setVisibility(View.GONE);
        } else {
            // Hide the corresponding FrameLayout and show the other if in portrait
            fragmentContainer2.setVisibility(View.GONE);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                fragmentContainer1.setVisibility(View.VISIBLE);
        }
    }

}
