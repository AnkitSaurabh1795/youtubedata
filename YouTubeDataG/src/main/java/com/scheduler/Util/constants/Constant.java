package com.scheduler.Util.constants;

import com.scheduler.Util.config.DbConfig;

import java.util.Arrays;
import java.util.Collection;

public interface Constant {
    public static  String CLIENT_SECRETS = "client_secret_630239214701-8ontludbj7f6tvd3v0dhrtuf736jut9r.apps.googleusercontent.com.json";
    public static final Collection<String> SCOPES =
            Arrays.asList("https://www.googleapis.com/auth/youtube.force-ssl");
    public static final String APPLICATION_NAME = "MyYoutubeIntegration";
    public static final String AUTHORIZATION_USER = "ankit.saurabh@uni.club";

    public static String MONGO_URI = "mongodb://localhost:27017";
}
