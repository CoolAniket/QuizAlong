package com.retrytech.quizbox.api;
//testing dev branch
import com.retrytech.quizbox.model.categories.Categories;
import com.retrytech.quizbox.model.home.HomePage;
import com.retrytech.quizbox.model.leaderboard.LeaderBoard;
import com.retrytech.quizbox.model.notification.Notifications;
import com.retrytech.quizbox.model.questions.Questions;
import com.retrytech.quizbox.model.quiz.QuizByCatId;
import com.retrytech.quizbox.model.redeemrequest.RedeemRequest;
import com.retrytech.quizbox.model.rest.RestResponse;
import com.retrytech.quizbox.model.settings.Settings;
import com.retrytech.quizbox.model.user.CurrentUser;

import java.util.HashMap;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiService {
    @FormUrlEncoded
    @POST(Const.USER_REGISTER)
    Single<CurrentUser> registerUser(@Header(Const.API_KEY) String apiKey,
                                     @FieldMap HashMap<String, String> hashMap);

    @POST(Const.QUIZ_HOMEPAGE)
    Single<HomePage> getHomePage(@Header(Const.API_KEY) String apiKey);

    @POST(Const.QUIZ_ALLCATEGORIES)
    Single<Categories> getAllCategories(@Header(Const.API_KEY) String apiKey);

    @FormUrlEncoded
    @POST(Const.USER_GET_USER_PROFILE)
    Single<CurrentUser> getUserProfile(@Header(Const.API_KEY) String apiKey,
                                       @Field(Const.USER_ID) String userId);

    @FormUrlEncoded
    @POST(Const.QUIZ_QUIZBYCATEGORY)
    Single<QuizByCatId> getQuizByCatId(@Header(Const.API_KEY) String apiKey,
                                       @Field(Const.CATID) String userId);

    @FormUrlEncoded
    @POST(Const.QUIZ_QUESTIONS_OF_QUIZ)
    Single<Questions> getQuestionsByQuizId(@Header(Const.API_KEY) String apiKey,
                                           @Field(Const.QUIZ_ID) String userId);

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

    @FormUrlEncoded
    @POST(Const.USER_PLACE_REDEEM)
    Single<RestResponse> addRedeemRequest(@Header(Const.API_KEY) String apiKey,
                                          @Field(Const.AMOUNT) String amount,
                                          @Field(Const.PAYMENT_METHOD) String paymentMethod,
                                          @Field(Const.ACCOUNT) String account,
                                          @Field(Const.USER_ID) String userId);

    @POST(Const.APP_LEADERBOARD)
    Single<LeaderBoard> getLeaderBoard(@Header(Const.API_KEY) String apiKey);

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
