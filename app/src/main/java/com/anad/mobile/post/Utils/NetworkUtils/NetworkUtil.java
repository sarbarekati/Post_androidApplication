package com.anad.mobile.post.Utils.NetworkUtils;


import java.util.HashSet;

public class NetworkUtil {


    public static String buildCookie(HashSet<String> cookies) {
        StringBuilder builder = new StringBuilder();
        for (String cookie : cookies) {
            builder.append(cookie);
            builder.append(";");
        }
        return builder.toString() + ";";
    }


}
