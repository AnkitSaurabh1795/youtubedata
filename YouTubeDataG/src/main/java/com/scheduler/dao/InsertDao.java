package com.scheduler.dao;

import com.google.api.services.youtube.model.Video;
import com.scheduler.Util.helper.MongoDbHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class InsertDao {
    private final MongoDbHelper dpHelper;
    public void insertVideo(Video video){
        dpHelper.saveData(video);
    }
}
