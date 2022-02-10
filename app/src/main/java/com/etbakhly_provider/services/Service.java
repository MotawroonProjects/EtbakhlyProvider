package com.etbakhly_provider.services;


import com.etbakhly_provider.model.CountryDataModel;
import com.etbakhly_provider.model.OrderDataModel;
import com.etbakhly_provider.model.PlaceGeocodeData;
import com.etbakhly_provider.model.PlaceMapDetailsData;
import com.etbakhly_provider.model.SingleOrderDataModel;
import com.etbakhly_provider.model.StatusResponse;
import com.etbakhly_provider.model.UserModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
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

    @GET("place/findplacefromtext/json")
    Call<PlaceMapDetailsData> searchOnMap(@Query(value = "inputtype") String inputtype,
                                          @Query(value = "input") String input,
                                          @Query(value = "fields") String fields,
                                          @Query(value = "language") String language,
                                          @Query(value = "key") String key
    );

    @GET("geocode/json")
    Call<PlaceGeocodeData> getGeoData(@Query(value = "latlng") String latlng,
                                      @Query(value = "language") String language,
                                      @Query(value = "key") String key);

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

    @FormUrlEncoded
    @POST("api/Catering/signup")
    Single<Response<UserModel>> signUp(@Field("name") String name,
                                       @Field("phone_code") String phone_code,
                                       @Field("phone") String phone,
                                       @Field("email") String email,
                                       @Field("longitude") String longitude,
                                       @Field("latitude") String latitude,
                                       @Field("type") String type,
                                       @Field("software_type") String software_type


    );

    @GET("api/Catering/governorates")
    Single<Response<CountryDataModel>> getCountry();

    @GET("api/Catering/cities")
    Single<Response<CountryDataModel>> getCityByCountryId(@Query("governorates_id") String country_id);

    @GET("api/Catering/zones")
    Single<Response<CountryDataModel>> getZone(@Query("city_id") String city_id);

    @GET("api/Catering/SingelOrder")
    Single<Response<SingleOrderDataModel>> getOrderDetails(@Query("order_id") String order_id);
}