package com.valentun.findgift.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
//--------------------------------------
//TODO navDrawer
//--------------------------------------
//TODO Save(star) functionality
//--------------------------------------
//TODO credentials page
//TODO settings page
//--------------------------------------

public class MainActivity extends ApiActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    private static final String CONTENT_TAG = "MainContent";
    private FragmentManager fragmentManager;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!SessionManager.isSessionStarted()) {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);

            finish();
            return;
        }

        ButterKnife.bind(this);

        initializeNavDrawer();

        fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentByTag(CONTENT_TAG);
        if (fragment == null) {
            setInitialState();
        }
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
                break;
            case R.id.nav_about:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_logout:
        }
        if (fragmentClass != null) replaceFragmentFromClass(fragmentClass);

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void setInitialState() {
        fragmentManager.beginTransaction()
                .add(R.id.main_fragment_container, new FindGiftFragment(), CONTENT_TAG)
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

    private void replaceFragmentFromClass(Class<? extends Fragment> fragmentClass){
        try {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, fragmentClass.newInstance())
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
