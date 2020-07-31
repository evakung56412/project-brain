package com.man_jou.projectbrain.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.man_jou.projectbrain.R;
import com.man_jou.projectbrain.fragment.CiteIdeaFragment;
import com.man_jou.projectbrain.fragment.HomeFragment;
import com.man_jou.projectbrain.fragment.NewIdeaFragment;
import com.man_jou.projectbrain.fragment.OriginalIdeaFragment;
import com.man_jou.projectbrain.fragment.ProfileFragment;
import com.man_jou.projectbrain.fragment.UpdateProfileFragment;
import com.man_jou.projectbrain.fragment.UserFollowersFragment;
import com.man_jou.projectbrain.fragment.UserIdeasFragment;
import com.man_jou.projectbrain.fragment.UserTodosFragment;

public class HomeActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    private TabLayout tabLayout;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();
    }

    public void initialize() {
        bundle = new Bundle();
        bundle.putString("username", getIntent().getStringExtra("username"));

        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home).setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_idea).setText("New Idea"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_profile).setText("Profile"));

        tabLayout.selectTab(tabLayout.getTabAt(0));

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment)
                .addToBackStack(fragment.getClass().getName()).commit();

        tabLayout.addOnTabSelectedListener(this);
    }

    /*
        TabLayout.OnTabSelectedListener
    */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        Fragment currentFrag = getSupportFragmentManager().findFragmentById(R.id.frameLayout);

        if (tab.getPosition() == 0) {
            HomeFragment fragment = new HomeFragment();
            fragment.setArguments(bundle);
;
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment)
                    .addToBackStack(fragment.getClass().getName()).commit();
        }
        else if (tab.getPosition() == 1) {
            NewIdeaFragment fragment = new NewIdeaFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment)
                    .addToBackStack(fragment.getClass().getName()).commit();
        }
        else {
            ProfileFragment fragment = new ProfileFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment)
                    .addToBackStack(fragment.getClass().getName()).commit();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}