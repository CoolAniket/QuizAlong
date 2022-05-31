package com.thequizapp.quizalong.utils;


import android.text.TextUtils;

import com.thequizapp.quizalong.api.ApiService;
import com.thequizapp.quizalong.api.Const;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Global {

    public static final AtomicReference<String> userId = new AtomicReference<>("");

    /*
    * already logged in account, app restart, shows aniket's profile
    * logout login, original name came up.
    * */
    Global() {
    }


    public static ApiService initRetrofit() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor interceptor = chain -> {
            Request request = chain.request();
            Request newRequest = request.newBuilder()
                    //.addHeader("secret_key", Global.ACCESS_TOKEN)
                    .build();
            return chain.proceed(newRequest);
        };
        OkHttpClient.Builder builder =
                new OkHttpClient.Builder();
        builder.networkInterceptors().add(interceptor);
        OkHttpClient client = builder.addInterceptor(loggingInterceptor)
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(ApiService.class);
    }

    public static String prettyCount(Number number) {
        try {
            char[] suffix = {' ', 'k', 'M', 'B', 'T', 'P', 'E'};
            long numValue = number.longValue();
            int value = (int) Math.floor(Math.log10(numValue));
            int base = value / 3;
            if (value >= 3 && base < suffix.length) {
                double value2 = numValue / Math.pow(10, base * (double) 3);
                if (String.valueOf(value2).contains(".")) {
                    String num = String.valueOf(value2).split("\\.")[String.valueOf(value2).split("\\.").length - 1];
                    if (num.contains("0")) {
                        return new DecimalFormat("#0").format(value2) + suffix[base];
                    } else {
                        return new DecimalFormat("#0.0").format(value2) + suffix[base];
                    }
                } else {
                    return new DecimalFormat("#0").format(value2) + suffix[base];
                }
            } else {
                return new DecimalFormat("#,##0").format(numValue);
            }
        } catch (Exception e) {
            return String.valueOf(number);
        }
    }

    public static String prettyAmount(Number number, String prefix) {
        try {
            int value = number.intValue();
            if (number.intValue() > 0 ) {
                return prefix + value;
            } else {
                return "Free";
            }
        } catch (Exception e) {
            return String.valueOf(number);
        }
    }

    public static String prettyNullId(String value) {
        try {
            if (TextUtils.isEmpty(value) ) {
                return "--";
            } else {
                return value;
            }
        } catch (Exception e) {
            return "--";
        }
    }
    public static String prettyPaymentStatus(String value) {
        try {
            if (TextUtils.isEmpty(value) ) {
                return "--";
            } else if (value.equals("Success")) {
                return "PAYMENT SUCCESSFUL";
            } else if (value.equals("Pending")) {
                return "PAYMENT PENDING";
            } else if (value.equals("Failure")) {
                return "PAYMENT FAILED";
            } else {
                return value;
            }
        } catch (Exception e) {
            return "--";
        }
    }
    public static String prettyWinningStatus(String value) {
        try {
            if (TextUtils.isEmpty(value) ) {
                return "--";
            } else if (value.equals("Success")) {
                return "WINNINGS CREDITED";
            } else if (value.equals("Pending")) {
                return "WINNINGS PENDING";
            } else if (value.equals("Failure")) {
                return "WINNINGS FAILED";
            } else {
                return value;
            }
        } catch (Exception e) {
            return "--";
        }
    }


}
