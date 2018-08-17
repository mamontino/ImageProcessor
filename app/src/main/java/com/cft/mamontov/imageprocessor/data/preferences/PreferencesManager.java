package com.cft.mamontov.imageprocessor.data.preferences;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class PreferencesManager implements PreferencesHelper {

    private static final String PREF_KEY_CURRENT_IMAGE = "PREF_KEY_CURRENT_IMAGE";

    private SharedPreferences mPreferences;

    @Inject
    PreferencesManager(SharedPreferences preferences) {
        this.mPreferences = preferences;
    }

    @Override
    public void setCurrentImage(String image) {
        mPreferences.edit().putString(PREF_KEY_CURRENT_IMAGE, image).apply();
    }

    @Override
    public String getCurrentImage() {
        return mPreferences.getString(PREF_KEY_CURRENT_IMAGE, "");
    }
}
