package com.example.lms_platfom.config.youtube;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class YouTubeService {

    @Value("${youtube.api.key}")
    private String apiKey;

    private final YouTube youtube;

    public YouTubeService() throws GeneralSecurityException, IOException {
        this.youtube = new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                request -> {}
        )
                .setApplicationName("YouTubeIntegration")
                .build();
    }
    public List<SearchResult> searchVideos(String query) throws IOException {
        try {
            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(apiKey);
            search.setQ(query);
            search.setType("video");
            search.setMaxResults(5L);

            SearchListResponse response = search.execute();
            return response.getItems();
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while fetching YouTube search results", e);
        }
    }
}
