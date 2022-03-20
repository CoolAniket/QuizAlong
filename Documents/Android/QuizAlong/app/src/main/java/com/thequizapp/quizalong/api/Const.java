package com.thequizapp.quizalong.api;

import com.thequizapp.quizalong.BuildConfig;

public final class Const {
    public static final String BASE_URL = "https://----/api/";
    public static final String ITEM_BASE_URL = "https://----/public/storage/";
    //Api End Point
    public static final String USER_REGISTER = "user/register";
    public static final String QUIZ_HOMEPAGE = "quiz/homepage";
    public static final String QUIZ_ALLCATEGORIES = "quiz/allcategories";
    public static final String USER_GET_USER_PROFILE = "user/getUserProfile";
    public static final String QUIZ_QUIZBYCATEGORY = "quiz/quizbycategory";
    public static final String QUIZ_QUESTIONS_OF_QUIZ = "quiz/questionsOfQuiz";
    public static final String USER_ADD_POINTS_TO_WALLET = "user/addPointsToWallet";
    public static final String USER_INCREASE_PLAY = "user/increasePlay";
    public static final String APP_SETTINGS = "app/settings";
    public static final String USER_EDIT_PROFILE = "user/editProfile";
    public static final String USER_PLACE_REDEEM = "user/placeRedeem";
    public static final String APP_LEADERBOARD = "app/leaderboard";
    public static final String USER_FETCH_REDEEMS = "user/fetchRedeems";
    public static final String APP_NOTIFICATIONS = "app/notifications";
    //Header
    public static final String API_KEY = "apiKey";
    //Field
    public static final String USER_ID = "userId";
    public static final String CATID = "catid";
    public static final String QUIZ_ID = "quizId";
    public static final String POINTS = "points";
    public static final String PLATFORM = "platform";
    public static final String AMOUNT = "amount";
    public static final String PAYMENT_METHOD = "payment_method";
    public static final String ACCOUNT = "account";
    public static final String START = "start";
    public static final String COUNT = "count";
    public static final String PROFILE_IMAGE = "profileImage";
    //SharedPreference
    public static final String USER = "user";
    public static final String APPLICATION_ID = BuildConfig.APPLICATION_ID;
    public static final String STATUS = "status";
    public static final int POST_TYPE = 0;
    public static final int AD_TYPE = 1;
    public static final int AD_FB_TYPE = 2;
    public static final int AD_CUSTOM_TYPE = 3;

    Const() {

    }

}
