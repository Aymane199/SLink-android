package com.ensim.mic.slink.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.ensim.mic.slink.Fragment.FoldersFragment;
import com.ensim.mic.slink.Fragment.PreferencesFragment;
import com.ensim.mic.slink.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {


    Fragment fragmentExplore;
    Fragment fragmentFolders;
    Fragment fragmentPreferences;


    String currentFragementTag = "";
    String fragmentFoldersTag = "fragmentFoldersTag";
    String fragmentPreferencesTag = "fragmentPreferencesTag";

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fragmentExplore = new ExploreFragment();
        fragmentFolders = new FoldersFragment();
        fragmentPreferences = new PreferencesFragment();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.fragment_container, fragmentFolders, fragmentFoldersTag);
        transaction.add(R.id.fragment_container, fragmentPreferences, fragmentPreferencesTag);
        //transaction.addToBackStack(null);

        transaction.show(fragmentFolders);
        transaction.hide(fragmentPreferences);
        transaction.commit();
        currentFragementTag = fragmentFoldersTag;


        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);



    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    switch (menuItem.getItemId()) {
                        // TODO add explore button (in the xml navigation too)
                 /*       case R.id.navigation_explore:
                            dest = fragmentManager.findFragmentByTag(ExploreFragment.class.getName());
                            currentFragementTag = ExploreFragment.class.getName();
                            if (dest == null) {
                                Log.d("TRANSACTION", "instanciating new navigation_explore");
                                dest = fragmentExplore;
                                transaction.add(R.id.fragment_container, dest, ExploreFragment.class.getName());
                            }
                            break;*/
                        case R.id.navigation_folders:
                            if(!currentFragementTag.equals(fragmentFoldersTag)){
                                transaction.hide(Objects.requireNonNull(fragmentManager.findFragmentByTag(currentFragementTag)));

                                transaction.show(Objects.requireNonNull(fragmentManager.findFragmentByTag(fragmentFoldersTag)));

                                transaction.commit();
                                currentFragementTag = fragmentFoldersTag;
                            }else {
                                Toast.makeText(MainActivity.this,"already clicked"+currentFragementTag,Toast.LENGTH_LONG).show();
                            }
                            break;
                        case R.id.navigation_preferences:
                            if(!currentFragementTag.equals(fragmentPreferencesTag)){
                                transaction.hide(Objects.requireNonNull(fragmentManager.findFragmentByTag(currentFragementTag)));

                                transaction.show(Objects.requireNonNull(fragmentManager.findFragmentByTag(fragmentPreferencesTag)));

                                transaction.commit();
                                currentFragementTag = fragmentPreferencesTag;
                            }else
                                Toast.makeText(MainActivity.this,"already clicked"+currentFragementTag,Toast.LENGTH_LONG).show();
                            break;

                    }

                return true;
                }
            };

    boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
