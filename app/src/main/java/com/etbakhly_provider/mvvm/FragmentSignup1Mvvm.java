package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.R;
import com.etbakhly_provider.model.AddZoneModel;
import com.etbakhly_provider.model.CategoryDataModel;
import com.etbakhly_provider.model.CategoryModel;
import com.etbakhly_provider.model.CountryDataModel;
import com.etbakhly_provider.model.CountryModel;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentSignup1Mvvm extends AndroidViewModel {
    private static final String TAG = "FragmentSignup1Mvvm";

    private MutableLiveData<List<CountryModel>> countryLiveData;
    private MutableLiveData<List<CountryModel>> cityLiveData;
    private MutableLiveData<List<CountryModel>> zoneLiveData;

    private MutableLiveData<List<CategoryModel>> onCategorySuccess;
    private MutableLiveData<List<AddZoneModel>> onAddZoneSuccess;


    private CompositeDisposable disposable = new CompositeDisposable();


    public FragmentSignup1Mvvm(@NonNull Application application) {
        super(application);
        getOnAddZoneLiveData().setValue(new ArrayList<>());
    }


    public MutableLiveData<List<CountryModel>> getCountryLiveData() {
        if (countryLiveData == null) {
            countryLiveData = new MutableLiveData<>();
        }
        return countryLiveData;
    }


    public MutableLiveData<List<CountryModel>> getZoneLiveData() {
        if (zoneLiveData == null) {
            zoneLiveData = new MutableLiveData<>();
        }
        return zoneLiveData;
    }


    public MutableLiveData<List<CountryModel>> getCityLiveData() {
        if (cityLiveData == null) {
            cityLiveData = new MutableLiveData<>();
        }
        return cityLiveData;
    }


    public MutableLiveData<List<CategoryModel>> onCategoryDataSuccess() {
        if (onCategorySuccess == null) {
            onCategorySuccess = new MutableLiveData<>();
        }
        return onCategorySuccess;
    }

    public MutableLiveData<List<AddZoneModel>> getOnAddZoneLiveData() {
        if (onAddZoneSuccess == null) {
            onAddZoneSuccess = new MutableLiveData<>();
            onAddZoneSuccess.setValue(new ArrayList<>());

        }
        return onAddZoneSuccess;
    }

    public void addZoneLiveData(AddZoneModel addZoneModel) {
        getOnAddZoneLiveData().getValue().add(addZoneModel);
        getOnAddZoneLiveData().setValue(getOnAddZoneLiveData().getValue());
    }


    //_________________________hitting api_________________________________
    public void getCountries(Context context) {

        Api.getService(Tags.base_url)
                .getCountry()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CountryDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<CountryDataModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                List<CountryModel> countryModelList = new ArrayList<>();
                                countryModelList.add(new CountryModel(context.getString(R.string.choose_country)));
                                countryModelList.addAll(response.body().getData());
                                getCountryLiveData().setValue(countryModelList);


                                List<CountryModel> cityList = new ArrayList<>();
                                cityList.add(new CountryModel(context.getString(R.string.ch_city)));
                                getCityLiveData().setValue(cityList);

                                List<CountryModel> zoneList = new ArrayList<>();
                                zoneList.add(new CountryModel(context.getString(R.string.ch_zone)));
                                getZoneLiveData().setValue(zoneList);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "Error", e);
                    }
                });

    }

    public void getCity(Context context, String country_id) {

        Api.getService(Tags.base_url)
                .getCityByCountryId(country_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CountryDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<CountryDataModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                List<CountryModel> cityList = new ArrayList<>();
                                cityList.add(new CountryModel(context.getString(R.string.ch_city)));
                                cityList.addAll(response.body().getData());
                                getCityLiveData().setValue(cityList);

                                List<CountryModel> zoneList = new ArrayList<>();
                                zoneList.add(new CountryModel(context.getString(R.string.ch_zone)));
                                getZoneLiveData().setValue(zoneList);

                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "Error", e);
                    }
                });

    }


    public void getZone(String city_id, Context context) {

        Api.getService(Tags.base_url)
                .getZone(city_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CountryDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<CountryDataModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                List<CountryModel> zoneList = new ArrayList<>();
                                zoneList.add(new CountryModel(context.getString(R.string.ch_zone)));

                                if (response.body().getData().size() > 0) {
                                    zoneList.addAll(response.body().getData());

                                }

                                zoneLiveData.setValue(zoneList);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "Error", e);
                    }
                });

    }

    public void getCategoryData() {
        Api.getService(Tags.base_url).getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CategoryDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<CategoryDataModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                onCategorySuccess.setValue(response.body().getData());
                            }
                        } else {
                            try {
                                Log.e("slideError", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("error", e.getMessage());
                    }
                });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

    }

}
