package com.thequizapp.quizalong.api;
//testing dev branch
import com.thequizapp.quizalong.model.categories.CategoriesResponse;
import com.thequizapp.quizalong.model.home.HomePage;
import com.thequizapp.quizalong.model.home.TwistQuizPage;
import com.thequizapp.quizalong.model.leaderboard.LeaderBoardResponse;
import com.thequizapp.quizalong.model.notification.Notifications;
import com.thequizapp.quizalong.model.payment.TransactionResponse;
import com.thequizapp.quizalong.model.questions.NewQuestions;
import com.thequizapp.quizalong.model.quiz.AddDataLiveResponse;
import com.thequizapp.quizalong.model.payment.OrderResponse;
import com.thequizapp.quizalong.model.quiz.QuizByCatId;
import com.thequizapp.quizalong.model.redeemrequest.RedeemRequest;
import com.thequizapp.quizalong.model.rest.RestResponse;
import com.thequizapp.quizalong.model.results.ShowResultsRequest;
import com.thequizapp.quizalong.model.settings.Settings;
import com.thequizapp.quizalong.model.user.CurrentUser;

import java.util.HashMap;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST(Const.USER_REGISTER)
    Single<CurrentUser> registerUser(@Header(Const.API_KEY) String apiKey,
                                      @FieldMap HashMap<String, String> hashMap);

    @POST(Const.QUIZ_HOMEPAGE)
    Single<HomePage> getHomePage(@Header(Const.API_KEY) String apiKey);

    @GET(Const.QUIZ_TWIST)
    Single<TwistQuizPage> getTwistQuizPage(@Header(Const.API_KEY) String apiKey,
                                           @Path(Const.USER_ID) String userId);

    @GET(Const.QUIZ_UPCOMING)
    Single<TwistQuizPage> getUpcomingQuizPage(@Header(Const.API_KEY) String apiKey,
                                              @Path(Const.USER_ID) String userId);

    @GET(Const.QUIZ_PAST)
    Single<TwistQuizPage> getPastQuizPage(@Header(Const.API_KEY) String apiKey,
                                          @Path(Const.USER_ID) String userId);

    @GET(Const.QUIZ_ALLCATEGORIES)
    Single<CategoriesResponse> getAllCategories(@Header(Const.API_KEY) String apiKey,
                                                @Path(Const.COURSE_ID) int course_id,
                                                @Path(Const.USER_ID) String userId);
    @GET(Const.QUIZ_MY_QUIZ)
    Single<QuizByCatId> getMyQuizes(@Header(Const.API_KEY) String apiKey,
                                                @Path(Const.USER_ID) String userId);

    @GET(Const.PAYMENT_RZP_ORDER_ID)
    Single<OrderResponse> getRzpOrderId(@Header(Const.API_KEY) String apiKey,
                                        @Path(Const.AMOUNT) int amount);

    @GET(Const.PAYMENT_TRANSACTION_HISTORY)
    Single<TransactionResponse> getTransactionHistory(@Header(Const.API_KEY) String apiKey,
                                                      @Path(Const.USER_ID) int userId);
    @FormUrlEncoded
    @POST(Const.PAYMENT_ADD_PAYMENT_DATA)
    Single<RestResponse> addPaymentData(@Header(Const.API_KEY) String apiKey,
                                                @FieldMap HashMap<String, String> requestBodyHashMap);
    @FormUrlEncoded
    @POST(Const.QUIZ_USER_CATEGORIES)
    Single<RestResponse> saveUserCategory(@Header(Const.API_KEY) String apiKey,
                                                @FieldMap HashMap<String, Integer> requestBodyHashMap);

    @FormUrlEncoded
    @POST(Const.QUIZ_USER_CATEGORIES_BULK)
    Single<RestResponse> saveUserCategoriesBulk(@Header(Const.API_KEY) String apiKey,
                                                @FieldMap HashMap<String, Integer> requestBodyHashMap);

    @FormUrlEncoded
    @POST(Const.USER_SEND_OTP)
    Single<RestResponse> sentOTPonMobile(@Header(Const.API_KEY) String apiKey,
                                                @FieldMap HashMap<String, String> requestBodyHashMap);
    @FormUrlEncoded
    @POST(Const.USER_VERIFY_OTP)
    Single<RestResponse> verifyOTPonMobile(@Header(Const.API_KEY) String apiKey,
                                                @FieldMap HashMap<String, String> requestBodyHashMap);


    @FormUrlEncoded
    @POST(Const.USER_GET_USER_PROFILE)
    Single<CurrentUser> getUserProfile(@Header(Const.API_KEY) String apiKey,
                                       @Field(Const.USERID) String userId);

    @FormUrlEncoded
    @POST(Const.QUIZ_QUIZBYCATEGORY)
    Single<QuizByCatId> getQuizByCatId(@Header(Const.API_KEY) String apiKey,
                                       @Field(Const.CATID) String userId);

    @FormUrlEncoded
    @POST(Const.QUIZ_ADD_GAME_DATA_LIVE)
    Single<AddDataLiveResponse> addGameDataLive(@Header(Const.API_KEY) String apiKey,
                                                @FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST(Const.QUIZ_ADD_GAME_DATA_PAST)
    Single<AddDataLiveResponse> addGameDataPast(@Header(Const.API_KEY) String apiKey,
                                                @FieldMap HashMap<String, String> hashMap);

    @GET(Const.QUIZ_QUESTIONS_OF_QUIZ)
    Single<NewQuestions> getQuestionsByQuizId(@Header(Const.API_KEY) String apiKey,
                                              @Path(Const.QUIZ_ID_NEW) String quizId,
                                              @Path(Const.USER_ID) String userId);

    @GET(Const.QUIZ_ANS_LIVE)
    Single<ShowResultsRequest> getQuizAnsLive(@Header(Const.API_KEY) String apiKey,
                                              @Path(Const.QUIZ_ID_NEW) String quizId,
                                              @Path(Const.USER_ID) String userId);

    @GET(Const.APP_PAST_LEADERBOARD)
    Single<LeaderBoardResponse> getPastLeaderBoard(@Header(Const.API_KEY) String apiKey,
                                                   @Path(Const.QUIZ_ID_NEW) String quizId,
                                                   @Path(Const.USER_ID) String userId);

    @GET(Const.APP_LIVE_LEADERBOARD)
    Single<LeaderBoardResponse> getLiveLeaderBoard(@Header(Const.API_KEY) String apiKey,
                                                   @Path(Const.QUIZ_ID_NEW) String quizId);

    @GET(Const.QUIZ_ANS_PAST)
    Single<ShowResultsRequest> getQuizAnsPast(@Header(Const.API_KEY) String apiKey,
                                              @Path(Const.QUIZ_ID_NEW) String quizId,
                                              @Path(Const.USER_ID) String userId);
    @FormUrlEncoded
    @POST(Const.USER_ADD_POINTS_TO_WALLET)
    Single<RestResponse> addPointsToWallet(@Header(Const.API_KEY) String apiKey,
                                           @Field(Const.USER_ID) String userId,
                                           @Field(Const.POINTS) String points);

    @FormUrlEncoded
    @POST(Const.USER_INCREASE_PLAY)
    Single<RestResponse> increasePlay(@Header(Const.API_KEY) String apiKey,
                                      @Field(Const.USER_ID) String userId,
                                      @Field(Const.QUIZ_ID) String points);

    @FormUrlEncoded
    @POST(Const.APP_SETTINGS)
    Single<Settings> getAllSettings(@Header(Const.API_KEY) String apiKey,
                                    @Field(Const.PLATFORM) String platform);

    @Multipart
    @POST(Const.USER_EDIT_PROFILE)
    Single<RestResponse> editProfile(@Header(Const.API_KEY) String apiKey,
                                     @PartMap HashMap<String, RequestBody> requestBodyHashMap,
                                     @Part MultipartBody.Part profileImage);

    @Multipart
    @POST(Const.ADDITIONAL_DETAILS)
    Single<RestResponse> additionDetails(@Header(Const.API_KEY) String apiKey,
                                     @PartMap HashMap<String, RequestBody> requestBodyHashMap,
                                     @Part MultipartBody.Part profileImage);

    @FormUrlEncoded
    @POST(Const.USER_PLACE_REDEEM)
    Single<RestResponse> addRedeemRequest(@Header(Const.API_KEY) String apiKey,
                                          @Field(Const.AMOUNT) String amount,
                                          @Field(Const.PAYMENT_METHOD) String paymentMethod,
                                          @Field(Const.ACCOUNT) String account,
                                          @Field(Const.USER_ID) String userId);

    @FormUrlEncoded
    @POST(Const.USER_FETCH_REDEEMS)
    Single<RedeemRequest> fetchRedeemsRequests(@Header(Const.API_KEY) String apiKey,
                                               @Field(Const.USER_ID) String userId,
                                               @Field(Const.STATUS) String status);

    @FormUrlEncoded
    @POST(Const.APP_NOTIFICATIONS)
    Single<Notifications> getNotifications(@Header(Const.API_KEY) String apiKey,
                                           @Field(Const.START) String start,
                                           @Field(Const.COUNT) String count);
}
