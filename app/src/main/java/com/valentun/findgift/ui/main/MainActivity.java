package com.valentun.findgift.ui.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.valentun.findgift.R;
import com.valentun.findgift.persistence.SessionManager;
import com.valentun.findgift.ui.abstracts.ApiActivity;
import com.valentun.findgift.ui.auth.AuthActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ApiActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    private static final String CONTENT_TAG = "MainContent";
    private static final String MODEL_TAG = "MainModel";
    private FragmentManager fragmentManager;
    private ActionBarDrawerToggle toggle;
    private MainModelFragment modelFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!SessionManager.isSessionStarted()) {
            goToAuth();
            return;
        }

        ButterKnife.bind(this);

        initializeNavDrawer();

        fragmentManager = getFragmentManager();

        modelFragment = (MainModelFragment) fragmentManager.findFragmentByTag(MODEL_TAG);
        if (modelFragment == null) {
            setInitialState();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, modelFragment.getCurrentFragment(), CONTENT_TAG)
                    .commit();
        }
    }

    private void goToAuth() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Class<? extends Fragment> fragmentClass = null;
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.nav_find_gift:
                fragmentClass = FindGiftFragment.class;
                break;
            case R.id.nav_stars:
                fragmentClass = StarredFragment.class;
                break;
            case R.id.nav_about:
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.nav_logout:
                SessionManager.finishSession();
                goToAuth();
                return true;
        }
        if (fragmentClass != null) replaceFragmentFromClass(fragmentClass);

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void setInitialState() {
        modelFragment = new MainModelFragment();
        modelFragment.setCurrentFragment(new FindGiftFragment());

        fragmentManager.beginTransaction()
                .add(modelFragment, MODEL_TAG)
                .add(R.id.main_fragment_container, modelFragment.getCurrentFragment(), CONTENT_TAG)
                .commit();
        navigationView.setCheckedItem(R.id.nav_find_gift);
    }

    private void initializeNavDrawer() {
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.setFitsSystemWindows(true);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void replaceFragmentFromClass(Class<? extends android.app.Fragment> fragmentClass){
        try {
            android.app.Fragment fragment = fragmentClass.newInstance();
            modelFragment.setCurrentFragment(fragment);

            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, fragment)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
