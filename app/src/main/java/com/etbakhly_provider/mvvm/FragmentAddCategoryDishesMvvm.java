package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.R;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.CategoryModel;
import com.etbakhly_provider.model.DishModel;
import com.etbakhly_provider.model.DishesDataModel;
import com.etbakhly_provider.model.SingleCategory;
import com.etbakhly_provider.model.StatusResponse;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.tags.Tags;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentAddCategoryDishesMvvm extends AndroidViewModel {

    private MutableLiveData<List<BuffetModel.Category>> onCategorySuccess;
    private MutableLiveData<BuffetModel.Category> onSelectedCategory;
    private MutableLiveData<Boolean> onCategoryAddedSuccess;
    private CompositeDisposable disposable = new CompositeDisposable();


    public FragmentAddCategoryDishesMvvm(@NonNull Application application) {
        super(application);
        List<BuffetModel.Category> list = new ArrayList<>();


        BuffetModel.Category model1 = new BuffetModel.Category();
        model1.setTitel(application.getString(R.string.dishes));
        model1.setId("dishe");


        BuffetModel.Category model2 = new BuffetModel.Category();
        model2.setTitel(application.getString(R.string.buffets));
        model2.setId("buffet");

        BuffetModel.Category model3 = new BuffetModel.Category();
        model3.setTitel(application.getString(R.string.banquets));
        model3.setId("feast");


        list.add(model1);
        list.add(model2);
        list.add(model3);

        getOnCategorySuccess().setValue(list);
        getOnSelectedCategorySuccess().setValue(model1);

    }

    public MutableLiveData<Boolean> getCategoryAddedSuccess() {
        if (onCategoryAddedSuccess == null) {
            onCategoryAddedSuccess = new MutableLiveData<>();
        }

        return onCategoryAddedSuccess;
    }

    public MutableLiveData<List<BuffetModel.Category>> getOnCategorySuccess() {
        if (onCategorySuccess == null) {
            onCategorySuccess = new MutableLiveData<>();
        }

        return onCategorySuccess;
    }

    public MutableLiveData<BuffetModel.Category> getOnSelectedCategorySuccess() {
        if (onSelectedCategory == null) {
            onSelectedCategory = new MutableLiveData<>();
        }

        return onSelectedCategory;
    }


    public void addCategory(String name, String caterer_id, Context context) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url).addCatererDish(name, caterer_id, getOnSelectedCategorySuccess().getValue().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleCategory>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleCategory> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                getOnCategorySuccess().getValue().add(response.body().getData());
                                getCategoryAddedSuccess().setValue(true);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();

                        Log.e("error", e.getMessage());
                    }
                });
    }


}
