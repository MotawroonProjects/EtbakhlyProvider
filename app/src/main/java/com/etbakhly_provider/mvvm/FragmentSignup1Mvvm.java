package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.model.CountryDataModel;
import com.etbakhly_provider.model.CountryModel;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentSignup1Mvvm extends AndroidViewModel {
    private static final String TAG = "FragmentSignup1Mvvm";
    private Context context;

    private MutableLiveData<List<CountryModel>> countryLiveData;
    private MutableLiveData<Boolean> isLoadingLivData;
    private MutableLiveData<List<CountryModel>> cityLiveData;

    private CompositeDisposable disposable = new CompositeDisposable();


    public FragmentSignup1Mvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }


    public MutableLiveData<List<CountryModel>> getCountryLiveData() {
        if (countryLiveData == null) {
            countryLiveData = new MutableLiveData<>();
        }
        return countryLiveData;
    }
    public MutableLiveData<List<CountryModel>> getCityLiveData() {
        if (cityLiveData == null) {
            cityLiveData = new MutableLiveData<>();
        }
        return cityLiveData;
    }



    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoadingLivData == null) {
            isLoadingLivData = new MutableLiveData<>();
        }
        return isLoadingLivData;
    }

    //_________________________hitting api_________________________________

    public void getCountries() {
        isLoadingLivData.setValue(true);

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
                        isLoadingLivData.setValue(false);
                        if (response.isSuccessful()) {
                            Log.d("status",response.body().getStatus()+"__");
                            if (response.body() != null && response.body().getStatus() == 200) {
                                countryLiveData.setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "Error", e);
                    }
                });

    }





    //_________________________hitting api_________________________________

    public void getCity(String country_id) {
        isLoadingLivData.setValue(true);

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
                        isLoadingLivData.setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                cityLiveData.setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "Error", e);
                    }
                });

    }



    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

    }

}