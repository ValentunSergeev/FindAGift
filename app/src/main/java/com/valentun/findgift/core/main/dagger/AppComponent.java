package com.valentun.findgift.core.main.dagger;

import com.valentun.findgift.core.main.handlers.AbstractGiftListHandler;
import com.valentun.findgift.ui.auth.AuthActivity;
import com.valentun.findgift.ui.main.FindGiftFragment;
import com.valentun.findgift.ui.main.SettingsFragment;
import com.valentun.findgift.ui.main.StarredFragment;
import com.valentun.findgift.ui.newgift.NewGiftActivity;
import com.valentun.findgift.ui.splash.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {NetModule.class})
@Singleton
public interface AppComponent {
    void inject(SplashActivity splashActivity);
    void inject(NewGiftActivity newGiftActivity);
    void inject(FindGiftFragment findGiftFragment);
    void inject(SettingsFragment settingsFragment);
    void inject(StarredFragment settingsFragment);
    void inject(AbstractGiftListHandler handler);
    void inject(AuthActivity authActivity);
}
