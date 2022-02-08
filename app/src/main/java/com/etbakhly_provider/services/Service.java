package com.etbakhly_provider.services;


import com.etbakhly_provider.model.OrderDataModel;
import com.etbakhly_provider.model.StatusResponse;
import com.etbakhly_provider.model.UserModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {


    @GET("api/Service/myOrder_provider")
    Single<Response<OrderDataModel>> getMyOrder(@Query(value = "caterer_id") String caterer_id,
                                                @Query(value = "is_end") String is_end);

    @FormUrlEncoded
    @POST("api/Catering/login")
    Single<Response<UserModel>> login(@Field("phone_code") String phone_code,
                                      @Field("phone") String phone,
                                      @Field("yes_i_read_it") String yes_i_read_it);

    @FormUrlEncoded
    @POST("api/Service/change_status_Order")
    Single<Response<StatusResponse>> changeStatusOrder(@Field("order_id") String order_id,
                                                       @Field("status_order") String status_order,
                                                       @Field("why_cancel") String why_cancel);
}