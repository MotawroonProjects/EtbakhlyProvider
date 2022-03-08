package com.etbakhly_provider.services;


import com.etbakhly_provider.model.AddBuffetDishDataModel;
import com.etbakhly_provider.model.AddDishDataModel;
import com.etbakhly_provider.model.AddFeastDishDataModel;
import com.etbakhly_provider.model.BuffetsDataModel;
import com.etbakhly_provider.model.CategoryDataModel;
import com.etbakhly_provider.model.CountryDataModel;
import com.etbakhly_provider.model.DishesDataModel;
import com.etbakhly_provider.model.OrderDataModel;
import com.etbakhly_provider.model.PlaceGeocodeData;
import com.etbakhly_provider.model.PlaceMapDetailsData;
import com.etbakhly_provider.model.SingleCategory;
import com.etbakhly_provider.model.SingleDishModel;
import com.etbakhly_provider.model.SingleKitchenDataModel;
import com.etbakhly_provider.model.SingleOrderDataModel;
import com.etbakhly_provider.model.StatusResponse;
import com.etbakhly_provider.model.AddBuffetDataModel;
import com.etbakhly_provider.model.StoreCatererDataModel;
import com.etbakhly_provider.model.UserModel;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    @POST("api/Catering/provider_login")
    Single<Response<UserModel>> login(@Field("phone_code") String phone_code,
                                      @Field("phone") String phone,
                                      @Field("yes_i_read_it") String yes_i_read_it);

    @FormUrlEncoded
    @POST("api/Service/change_status_Order")
    Single<Response<StatusResponse>> changeStatusOrder(@Field("order_id") String order_id,
                                                       @Field("status_order") String status_order,
                                                       @Field("why_cancel") String why_cancel);

    @Multipart
    @POST("api/Catering/signup")
    Single<Response<UserModel>> signUp(@Part("name") RequestBody name,
                                       @Part("phone_code") RequestBody phone_code,
                                       @Part("phone") RequestBody phone,
                                       @Part("email") RequestBody email,
                                       @Part("address") RequestBody address,
                                       @Part("longitude") RequestBody longitude,
                                       @Part("latitude") RequestBody latitude,
                                       @Part("type") RequestBody type,
                                       @Part("software_type") RequestBody software_type,
                                       @Part MultipartBody.Part part


    );

    @Multipart
    @POST("api/Service/update_profile_Provider")
    Single<Response<UserModel>> updateProfile(@Part("provider_id") RequestBody provider_id,
                                              @Part("name") RequestBody name,
                                              @Part("email") RequestBody email,
                                              @Part MultipartBody.Part part


    );

    @GET("api/Catering/governorates")
    Single<Response<CountryDataModel>> getCountry();

    @GET("api/Catering/cities")
    Single<Response<CountryDataModel>> getCityByCountryId(@Query("governorates_id") String country_id);

    @GET("api/Catering/zones")
    Single<Response<CountryDataModel>> getZone(@Query("city_id") String city_id);

    @GET("api/Catering/indexCategory")
    Single<Response<CategoryDataModel>> getCategories();

    @GET("api/Catering/SingelOrder")
    Single<Response<SingleOrderDataModel>> getOrderDetails(@Query("order_id") String order_id);

    @GET("api/Catering/CatererDetails")
    Single<Response<SingleKitchenDataModel>> getKitchenDetails(@Query(value = "Caterer_id") String caterer_id,
                                                               @Query("user_id") String user_id);

    @GET("api/Catering/indexCategoryDishes")
    Single<Response<DishesDataModel>> getDishes(@Query("category_dishes_id") String category_dishes_id,
                                                @Query("Caterer_id") String Caterer_id,
                                                @Query("type") String type,
                                                @Query(value = "category_type") String category_type,
                                                @Query(value = "category_type_id") String category_type_id

    );

    @GET("api/Catering/CatererBuffets")
    Single<Response<BuffetsDataModel>> getBuffets(@Query(value = "Caterer_id") String Caterer_id,
                                                  @Query(value = "user_type") String user_type

    );

    @GET("api/Catering/CatererFeasts")
    Single<Response<BuffetsDataModel>> getFeasts(@Query(value = "Caterer_id") String caterer_id,
                                                 @Query(value = "user_type") String user_type

    );

    @FormUrlEncoded
    @POST("api/Catering/delete_buffet")
    Single<Response<StatusResponse>> deleteBuffet(@Field("buffet_id") String buffet_id);

    @FormUrlEncoded
    @POST("api/Catering/delete_feast")
    Single<Response<StatusResponse>> deleteFeasts(@Field("feast_id") String feast_id);

    @Multipart
    @POST("api/Catering/storeBuffets")
    Single<Response<StatusResponse>> storeBuffet(@Part("titel") RequestBody titel,
                                                 @Part("number_people") RequestBody number_people,
                                                 @Part("service_provider_type") RequestBody service_provider_type,
                                                 @Part("order_time") RequestBody order_time,
                                                 @Part MultipartBody.Part photo,
                                                 @Part("price") RequestBody price,
                                                 @Part("category_dishes_id") RequestBody category_dishes_id,
                                                 @Part("caterer_id") RequestBody caterer_id);

    @Multipart
    @POST("api/Catering/updateBuffet")
    Single<Response<StatusResponse>> updateBuffet(@Part("titel") RequestBody titel,
                                                  @Part("number_people") RequestBody number_people,
                                                  @Part("service_provider_type") RequestBody service_provider_type,
                                                  @Part("order_time") RequestBody order_time,
                                                  @Part MultipartBody.Part photo,
                                                  @Part("price") RequestBody price,
                                                  @Part("category_dishes_id") RequestBody category_dishes_id,
                                                  @Part("Buffet_id") RequestBody Buffet_id);

    @Multipart
    @POST("api/Catering/storeDishes")
    Single<Response<AddDishDataModel>> storeDish(@Part("titel") RequestBody titel,
                                                 @Part("category_dishes_id") RequestBody category_dishes_id,
                                                 @Part("price") RequestBody price,
                                                 @Part("details") RequestBody details,
                                                 @Part MultipartBody.Part photo,
                                                 @Part("qty") RequestBody qty,
                                                 @Part("caterer_id") RequestBody caterer_id);

    @Multipart
    @POST("api/Catering/updateDishes")
    Single<Response<StatusResponse>> updateDish(@Part("titel") RequestBody titel,
                                                @Part("category_dishes_id") RequestBody category_dishes_id,
                                                @Part("price") RequestBody price,
                                                @Part("details") RequestBody details,
                                                @Part MultipartBody.Part photo,
                                                @Part("qty") RequestBody qty,
                                                @Part("dishes_id") RequestBody dishes_id);

    @FormUrlEncoded
    @POST("api/Catering/deleteDishes")
    Single<Response<StatusResponse>> deleteDish(@Field("dishes_id") String dishes_id);


    @POST("api/Service/storeCaterer")
    Single<Response<UserModel>> storeCaterer(@Body StoreCatererDataModel model
    );

    @Multipart
    @POST("api/Catering/storeBuffetsDishes")
    Single<Response<SingleDishModel>> storeBuffetsDishes(@Part("titel") RequestBody titel,
                                                         @Part("category_dishes_id") RequestBody category_dishes_id,
                                                         @Part("price") RequestBody price,
                                                         @Part("details") RequestBody details,
                                                         @Part MultipartBody.Part photo,
                                                         @Part("qty") RequestBody qty,
                                                         @Part("buffets_id") RequestBody buffets_id);

    @Multipart
    @POST("api/Catering/updateDishes")
    Single<Response<SingleDishModel>> updateBuffetsDishes(@Part("titel") RequestBody titel,
                                                          @Part("category_dishes_id") RequestBody category_dishes_id,
                                                          @Part("price") RequestBody price,
                                                          @Part("details") RequestBody details,
                                                          @Part MultipartBody.Part photo,
                                                          @Part("qty") RequestBody qty,
                                                          @Part("dishes_id") RequestBody dishes_id,
                                                          @Part("buffets_id") RequestBody buffets_id


    );


    @FormUrlEncoded
    @POST("api/Catering/CategoryDishes")
    Single<Response<SingleCategory>> addCatererDish(@Field("titel") String titel,
                                                    @Field("caterer_id") String caterer_id,
                                                    @Field("type") String type,
                                                    @Field("category_type") String category_type,
                                                    @Field("category_type_id") String category_type_id

    );

    @FormUrlEncoded
    @POST("api/Catering/CategoryDishesUpdate")
    Single<Response<StatusResponse>> editCatererDish(@Field("titel") String titel,
                                                     @Field("Category_Dishes_id") String Category_Dishes_id
    );

    @FormUrlEncoded
    @POST("api/Catering/CategoryDishesDelete")
    Single<Response<StatusResponse>> deleteCatererDish(@Field("Category_Dishes_id") String Category_Dishes_id
    );

    @Multipart
    @POST("api/Catering/storeFeastDishes")
    Single<Response<AddFeastDishDataModel>> storeFeastDishes(@Part("titel") RequestBody titel,
                                                             @Part("category_dishes_id") RequestBody category_dishes_id,
                                                             @Part("price") RequestBody price,
                                                             @Part("details") RequestBody details,
                                                             @Part MultipartBody.Part photo,
                                                             @Part("qty") RequestBody qty,
                                                             @Part("feast_id") RequestBody feast_id);

    @FormUrlEncoded
    @POST("api/Catering/logout")
    Single<Response<StatusResponse>> logout(@Field("user_id") String user_id,
                                            @Field("token") String phone_token


    );

    @FormUrlEncoded
    @POST("api/Catering/firebaseTokens")
    Single<Response<StatusResponse>> updateFireBaseToken(@Field("token") String token,
                                                         @Field("user_id") String user_id,
                                                         @Field("type") String type


    );

    @FormUrlEncoded
    @POST("api/Catering/CountactUs")
    Single<Response<StatusResponse>> contactUs(@Field("name") String name,
                                               @Field("email") String email,
                                               @Field("subject") String subject,
                                               @Field("message") String message


    );

    @Multipart
    @POST("api/Catering/storeFeast")
    Single<Response<StatusResponse>> storeFeast(@Part("titel") RequestBody titel,
                                                @Part("number_people") RequestBody number_people,
                                                @Part("service_provider_type") RequestBody service_provider_type,
                                                @Part("order_time") RequestBody order_time,
                                                @Part MultipartBody.Part photo,
                                                @Part("price") RequestBody price,
                                                @Part("category_dishes_id") RequestBody category_dishes_id,
                                                @Part("caterer_id") RequestBody caterer_id);

    @Multipart
    @POST("api/Catering/updateFeast")
    Single<Response<StatusResponse>> updateFeast(@Part("titel") RequestBody titel,
                                                 @Part("number_people") RequestBody number_people,
                                                 @Part("service_provider_type") RequestBody service_provider_type,
                                                 @Part("order_time") RequestBody order_time,
                                                 @Part MultipartBody.Part photo,
                                                 @Part("price") RequestBody price,
                                                 @Part("category_dishes_id") RequestBody category_dishes_id,
                                                 @Part("feast_id") RequestBody feast_id);

}