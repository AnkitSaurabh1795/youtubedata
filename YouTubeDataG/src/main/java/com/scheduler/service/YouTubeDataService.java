package com.scheduler.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.scheduler.Util.config.SearchQueryConfig;
import com.scheduler.dao.InsertDao;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport;
import static com.scheduler.Util.constants.Constant.*;

@Service
@RequiredArgsConstructor
public class YouTubeDataService {
    private final InsertDao insertDao;

    private final SearchQueryConfig searchQueryConfig;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final NetHttpTransport httpTransport;
    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static SearchListResponse response ;

    private static final Credential credential;

    static {
        try {
            credential = authorize(httpTransport);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //to trigger the scheduler every 10 seconds
    @Scheduled(fixedRate = 10000)
    public void scheduledTask1() throws IOException, GeneralSecurityException {
        String searchQuery = searchQueryConfig.getQuery();
        String publishedAfter = searchQueryConfig.getPublishedafter();
        System.out.println("YouTube Data scheduler also doing and access token is : " + credential.getAccessToken());

        YouTube youtubeService = getService();
        // Define and execute the API request
        YouTube.Search.List request = youtubeService.search()
                .list(Collections.singletonList("snippet"));
        if(Objects.isNull(response)){
            response = request
                    //.setChannelType("any")
                    .setPart(Collections.singletonList("snippet"))
                    .setType(Collections.singletonList("video"))
                    //.setEventType("none")
                    //.setForDeveloper(true)
                    .setMaxResults(25L)
                    .setOrder("date")
                    .setQ(searchQuery)
                    .setPublishedAfter(publishedAfter)
                    //.setQ("surfing")
                    .execute();
            System.out.println("inside first response query"+searchQuery +"and "+ publishedAfter);
        }else{
            response = request
                    //.setChannelType("any")
                    .setPart(Collections.singletonList("snippet"))
                    .setType(Collections.singletonList("video"))
                    //.setEventType("none")
                    //.setForDeveloper(true)
                    .setMaxResults(25L)
                    .setOrder("date")
                    .setQ(searchQuery)
                    .setPublishedAfter(publishedAfter)
                    .setPageToken(response.getNextPageToken())
                    .execute();
            System.out.println("inside else response query "+searchQuery +" and "+ publishedAfter);
        }
        // Get the list of videos from the search results
        List<SearchResult> searchResults = response.getItems();

        // Retrieve the data for each video
        for (SearchResult searchResult : searchResults) {
            Video video = youtubeService.videos().list(Collections.singletonList("snippet"))
                    .setId(Collections.singletonList(searchResult.getId().getVideoId()))
                    .execute()
                    .getItems()
                    .get(0);
            System.out.println(video);
            insertDao.insertVideo(video);
            // Create a Video entity and save it to the database
        }

    }

public static YouTube getService() throws GeneralSecurityException, IOException {
    final NetHttpTransport httpTransport = newTrustedTransport();

    System.out.println(credential.getAccessToken());
    return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
}

    public static Credential authorize(final NetHttpTransport httpTransport) throws IOException {
        // Load client secrets.
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(new FileInputStream(CLIENT_SECRETS)));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                        .build();
        Credential credential =
                new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(AUTHORIZATION_USER);
        return credential;
    }
}
