package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.R;
import com.etbakhly_provider.model.AddDishModel;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.CategoryDishModel;
import com.etbakhly_provider.model.CategoryModel;
import com.etbakhly_provider.model.DishModel;
import com.etbakhly_provider.model.CategoryDataModel;
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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ActivityDishesMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isDataLoading;
    private MutableLiveData<List<BuffetModel.Category>> onDataSuccess;
    private MutableLiveData<List<DishModel>> onDishesSuccess;
    private MutableLiveData<BuffetModel.Category> onEditSuccess;
    private MutableLiveData<Boolean> onDeleteSuccess;
    private MutableLiveData<Boolean> onDeleteDishSuccess;

    private MutableLiveData<Integer> selectedCategoryPos;
    private MutableLiveData<Integer> selectedDishPos = new MutableLiveData<>(-1);

    private CompositeDisposable disposable = new CompositeDisposable();


    public ActivityDishesMvvm(@NonNull Application application) {
        super(application);


    }


    public MutableLiveData<Integer> getSelectedDishPos() {
        if (selectedDishPos == null) {
            selectedDishPos = new MutableLiveData<>();
        }
        return selectedDishPos;
    }


    public MutableLiveData<Boolean> getOnDeleteDishSuccess() {
        if (onDeleteDishSuccess == null) {
            onDeleteDishSuccess = new MutableLiveData<>();
        }
        return onDeleteDishSuccess;
    }

    public MutableLiveData<Boolean> getIsDataLoading() {
        if (isDataLoading == null) {
            isDataLoading = new MutableLiveData<>();
        }
        return isDataLoading;
    }

    public MutableLiveData<Boolean> getOnDeleteSuccess() {
        if (onDeleteSuccess == null) {
            onDeleteSuccess = new MutableLiveData<>();
        }
        return onDeleteSuccess;
    }

    public MutableLiveData<List<BuffetModel.Category>> onDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }


    public MutableLiveData<Integer> getSelectedCategoryPos() {
        if (selectedCategoryPos == null) {
            selectedCategoryPos = new MutableLiveData<>(-1);
        }

        return selectedCategoryPos;
    }

    public void setSelectedCategoryPos(int pos) {
        getSelectedCategoryPos().setValue(pos);

    }

    public MutableLiveData<List<DishModel>> onDishSuccess() {
        if (onDishesSuccess == null) {
            onDishesSuccess = new MutableLiveData<>();
        }
        return onDishesSuccess;
    }

    public MutableLiveData<BuffetModel.Category> onEditSuccess() {
        if (onEditSuccess == null) {
            onEditSuccess = new MutableLiveData<>();
        }
        return onEditSuccess;
    }

    public void updateCategory(BuffetModel.Category category, int pos) {
        onDataSuccess().getValue().set(pos, category);
    }


    public void getDishes(String kitchen_id) {
        getIsDataLoading().setValue(true);
        Api.getService(Tags.base_url).getDishes("all", kitchen_id, "dishe","all",null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<DishesDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<DishesDataModel> response) {
                        getIsDataLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                List<BuffetModel.Category> categoryList = response.body().getData();
                                categoryList.add(0, null);
                                onDataSuccess.setValue(categoryList);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.getMessage());
                    }
                });
    }

    public void addCategory(String name, String caterer_id, Context context) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url).addCatererDish(name, caterer_id, "dishe", "all", null)
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

                                List<BuffetModel.Category> list = new ArrayList<>(onDataSuccess().getValue());
                                list.add(response.body().getData());
                                onDataSuccess().setValue(list);


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

    public void editCategory(BuffetModel.Category category, String name, String Category_Dishes_id, Context context, int pos) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        Api.getService(Tags.base_url).editCatererDish(name, Category_Dishes_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        dialog.dismiss();
                        Log.e("status2", response.body().getStatus() + "__");
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                category.setTitel(name);
                                onEditSuccess().setValue(category);


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


    public void deleteCategory(String Category_Dishes_id, Context context, int pos) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url).deleteCatererDish(Category_Dishes_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        dialog.dismiss();
                        Log.e("status", response.body().getStatus() + "__");

                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                onDataSuccess().getValue().remove(pos);
                                getOnDeleteSuccess().postValue(true);

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

    public void deleteDish(String dishes_id) {
        Api.getService(Tags.base_url).deleteDish(dishes_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                getOnDeleteDishSuccess().postValue(true);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.getMessage());
                    }
                });
    }


}
