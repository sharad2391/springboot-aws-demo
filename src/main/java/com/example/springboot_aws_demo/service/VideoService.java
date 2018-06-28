package com.example.springboot_aws_demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.springboot_aws_demo.vo.PlayListDetails;
import com.example.springboot_aws_demo.vo.Video;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class VideoService {

	@Autowired
	RestTemplate restTemplate;

	public Collection<PlayListDetails> getDataFromYoutubeService() {
		final String uri = "https://www.googleapis.com/youtube/v3/activities?part=snippet,contentDetails&channelId=UC59K-uG2A5ogwIrHw4bmlEg&key=AIzaSyAswyWngcFEjCF8-CkRhmCNPH-7esa24r0&maxResults=50";
		String result = restTemplate.getForObject(uri, String.class);
		
		System.out.println("Result fetched from Youtube");
		System.out.println(result);
		
		
		return processYoutubeJson(result);
		//return result;
	}

	private Collection<PlayListDetails> processYoutubeJson(String youtubeJson) {
		
		Collection<PlayListDetails> playListCollection = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			JsonNode rootNode = mapper.readTree(youtubeJson);
			JsonNode itemsNode = rootNode.path("items");

			
			Collection<Video> videosList = new ArrayList<>();
			Set<String> playListSet = createPlayListSet(itemsNode);
			for (String s : playListSet) {
				PlayListDetails playList = new PlayListDetails();
				playList.setPlaylistId(s);

				for (JsonNode items : itemsNode) {

					Video video = new Video();
					JsonNode snippetNode = items.path("snippet");
					if (!snippetNode.isMissingNode()) {
						video.setVideo_Title(snippetNode.path("title").asText());
						video.setVideo_Description(snippetNode.path("description").asText());
					}

					JsonNode contentDetailsNode = items.path("contentDetails");
					JsonNode playlistItem = contentDetailsNode.path("playlistItem");
					if (!playlistItem.isMissingNode()) {
						JsonNode resourceId = playlistItem.path("resourceId");
						video.setVideoId(resourceId.path("videoId").asText());

						if (playlistItem.path("playlistId").asText().equalsIgnoreCase(s)) {
							videosList.add(video);
						}

					}
				}
				playList.setVideos(videosList);
				playListCollection.add(playList);
			}

			
			
		} catch (IOException e) {
			System.out.println("Error in mappiing youtubeJson into tree of Json Nodes");
			e.printStackTrace();
		}
		return playListCollection;
	}

	private Set<String> createPlayListSet(JsonNode itemsNode) {
		Set<String> playListSet = new HashSet<>();
		for (JsonNode items : itemsNode) {

			JsonNode contentDetailsNode = items.path("contentDetails");
			JsonNode playlistItem = contentDetailsNode.path("playlistItem");
			if (!playlistItem.isMissingNode()) {
				playListSet.add(playlistItem.path("playlistId").asText());

			}
		}
		return playListSet;

	}

}
