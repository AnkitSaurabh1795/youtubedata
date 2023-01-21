package com.scheduler.Util.helper;

import com.google.api.services.youtube.model.Video;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.scheduler.Util.config.DbConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.scheduler.Util.constants.Constant.MONGO_URI;

@Service
@Slf4j
@RequiredArgsConstructor
public class MongoDbHelper {
    String COLLECTION_NAME = "video";

    MongoClient mongoClient = MongoClients.create(MONGO_URI);
    MongoDatabase database = mongoClient.getDatabase("youtubedata");

    public void saveData(Video video){

        try{
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            Document videoDoc = new Document("id", video.getId())  //searchResult.getId().getKind())
                    .append("kind",video.getKind())
                    .append("title", video.getSnippet().getTitle())
                    .append("publishedAt",String.valueOf(video.getSnippet().getPublishedAt()))
                    .append("tags", video.getSnippet().getTags());
            collection.insertOne(videoDoc);
        }catch (Exception e){
            throw e;
        }

    }

}
