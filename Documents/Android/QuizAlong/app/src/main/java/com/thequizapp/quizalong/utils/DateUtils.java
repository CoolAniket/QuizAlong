package com.thequizapp.quizalong.utils;

import android.util.Log;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm a", Locale.getDefault());
    private static SimpleDateFormat simpleDateFormat24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public static Date parseDateTime(String dateTimeString) throws ParseException {
        return simpleDateFormat.parse(dateTimeString);
    }

    public static Date parseDateTime24(String dateTimeString) throws ParseException {
        return simpleDateFormat24.parse(dateTimeString);
    }

    public static String format(Date date) {
        return simpleDateFormat.format(date);
    }


    //https://stackoverflow.com/a/14720918
    //NTP server list: http://tf.nist.gov/tf-cgi/servers.cgi
    public static final String TIME_SERVER = "time-a.nist.gov";
    // Server time network api call
    public static long getCurrentNetworkTime() {
        NTPUDPClient timeClient = new NTPUDPClient();
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(TIME_SERVER);
            TimeInfo timeInfo = timeClient.getTime(inetAddress);
            //long returnTime = timeInfo.getReturnTime();   //local device time
            long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();   //server time

            Date time = new Date(returnTime);
            Log.d("SERVER_TIME", "Time from " + TIME_SERVER + ": " + time);
            return returnTime;
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("SERVER_TIME", "Time from " + TIME_SERVER + ": " + -1);
        return -1;
    }
}
