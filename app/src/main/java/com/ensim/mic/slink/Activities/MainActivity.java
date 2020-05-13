package com.ensim.mic.slink.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.ensim.mic.slink.Fragment.ExploreFragment;
import com.ensim.mic.slink.Fragment.FoldersFragment;
import com.ensim.mic.slink.Fragment.PreferencesFragment;
import com.ensim.mic.slink.R;

public class MainActivity extends AppCompatActivity {


    Fragment fragmentExplore;
    Fragment fragmentFolders;
    Fragment fragmentPreferences;

    FragmentManager fragmentManager;

    String currentFragementTag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentExplore = new ExploreFragment();
        fragmentFolders = new FoldersFragment();
        fragmentPreferences = new PreferencesFragment();

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        currentFragementTag = ExploreFragment.class.getName();
        transaction.add(R.id.fragment_container, fragmentExplore, ExploreFragment.class.getName());
        transaction.show(fragmentExplore);

        transaction.commit();

        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Fragment currentlyShown = fragmentManager.findFragmentByTag(currentFragementTag);

                    Fragment dest;
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_explore:
                            dest = fragmentManager.findFragmentByTag(ExploreFragment.class.getName());
                            currentFragementTag = ExploreFragment.class.getName();
                            if (dest == null) {
                                Log.d("TRANSACTION", "instanciating new fragment");
                                dest = fragmentExplore;
                                transaction.add(R.id.fragment_container, dest, ExploreFragment.class.getName());
                            }
                            break;
                        case R.id.navigation_folders:
                            dest = fragmentManager.findFragmentByTag(FoldersFragment.class.getName());
                            currentFragementTag = FoldersFragment.class.getName();
                            if (dest == null) {
                                Log.d("TRANSACTION", "instanciating new fragment");
                                dest = fragmentFolders;
                                transaction.add(R.id.fragment_container, dest, FoldersFragment.class.getName());
                            }
                            break;
                        case R.id.navigation_preferences:
                            dest = fragmentManager.findFragmentByTag(PreferencesFragment.class.getName());
                            currentFragementTag = PreferencesFragment.class.getName();
                            if (dest == null) {
                                Log.d("TRANSACTION", "instanciating new fragment");
                                dest = fragmentPreferences;
                                transaction.add(R.id.fragment_container, dest, PreferencesFragment.class.getName());
                            }
                            break;

                        default:
                            throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                    }

                    if(currentlyShown != null)
                        transaction.hide(currentlyShown);
                    transaction.show(dest);
                    transaction.commit();
                return true;
                }
            };

}
