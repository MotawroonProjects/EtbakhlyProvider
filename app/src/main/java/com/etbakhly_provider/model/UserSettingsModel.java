package com.etbakhly_provider.model;

import java.io.Serializable;

public class UserSettingsModel implements Serializable {
    private boolean isLanguageSelected = false;
    private boolean showIntroSlider = true;
    private String option_id = "";

    public boolean isLanguageSelected() {
        return isLanguageSelected;
    }

    public void setLanguageSelected(boolean languageSelected) {
        isLanguageSelected = languageSelected;
    }

    public boolean isShowIntroSlider() {
        return showIntroSlider;
    }

    public void setShowIntroSlider(boolean showIntroSlider) {
        this.showIntroSlider = showIntroSlider;
    }


}
