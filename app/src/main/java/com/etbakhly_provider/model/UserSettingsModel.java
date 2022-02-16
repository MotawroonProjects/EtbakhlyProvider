package com.etbakhly_provider.model;

import java.io.Serializable;

public class UserSettingsModel implements Serializable {
    private boolean isLanguageSelected = false;
    private boolean showIntroSlider = true;
    private String option_id = "";
    private boolean isFirstTime = true;
    private CountryModel countryModel;
    private CountryModel cityModel;
    private SelectedLocation location;

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

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public boolean isFirstTime() {
        return isFirstTime;
    }

    public void setFirstTime(boolean firstTime) {
        isFirstTime = firstTime;
    }

    public CountryModel getCountryModel() {
        return countryModel;
    }

    public void setCountryModel(CountryModel countryModel) {
        this.countryModel = countryModel;
    }

    public CountryModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CountryModel cityModel) {
        this.cityModel = cityModel;
    }

    public SelectedLocation getLocation() {
        return location;
    }

    public void setLocation(SelectedLocation location) {
        this.location = location;
    }
}
