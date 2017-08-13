package com.valentun.findgift.ui.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class MainModelFragment extends Fragment {
    private Fragment currentFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }


    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }
}
