package com.cft.mamontov.imageprocessor.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.cft.mamontov.imageprocessor.utils.AppConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesManager implements PreferencesHelper {

    private static final String PREF_KEY_CURRENT_IMAGE = "PREF_KEY_CURRENT_IMAGE";

    private final SharedPreferences mPreferences;

    @Inject
    PreferencesManager(Context context) {
        mPreferences = context.getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
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
