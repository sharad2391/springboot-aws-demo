package com.example.springboot_aws_demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.springboot_aws_demo.vo.PlayList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class VideoService {

	@Autowired
	RestTemplate restTemplate;

	public Collection<PlayList> getPlayListDetailsFromYoutubeService() {

		Collection<PlayList> playListCollection = new ArrayList<>();

		final String uri = "https://www.googleapis.com/youtube/v3/playlists?part=snippet&channelId=UC59K-uG2A5ogwIrHw4bmlEg\r\n"
				+ "&key=AIzaSyAswyWngcFEjCF8-CkRhmCNPH-7esa24r0&maxResults=50";
		String playListJson = restTemplate.getForObject(uri, String.class);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode;
		try {
			rootNode = mapper.readTree(playListJson);
			if (!rootNode.path("nextPageToken").isMissingNode()) {
				String nextPageToken = rootNode.path("nextPageToken").asText();

				final String uri1 = "https://www.googleapis.com/youtube/v3/playlists?part=snippet&channelId=UC59K-uG2A5ogwIrHw4bmlEg\r\n"
						+ "&key=AIzaSyAswyWngcFEjCF8-CkRhmCNPH-7esa24r0&maxResults=50&pageToken=nextPageToken";
				String playListJson1 = restTemplate.getForObject(uri, String.class);
				getPlayListSet(playListJson1, playListCollection, rootNode);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Result fetched from Youtube");
		System.out.println(playListJson);

		return playListCollection;
	}

	private void getPlayListSet(String playListJson, Collection<PlayList> playListCollection, JsonNode rootNode) {

		JsonNode itemsNode = rootNode.path("items");

		for (JsonNode node : itemsNode) {
			PlayList playList = new PlayList();
			playList.setPlaylistId(node.path("id").asText());

			JsonNode snippetNode = node.path("snippet");
			if (!snippetNode.isMissingNode()) {
				playList.setTitle(snippetNode.path("title").asText());
			}
			playListCollection.add(playList);
		}

	}

	/*private Collection<PlayList> processYoutubeJson(String youtubeJson) {
		
		Collection<PlayList> playListCollection = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			JsonNode rootNode = mapper.readTree(youtubeJson);
			JsonNode itemsNode = rootNode.path("items");

			
			Collection<Video> videosList = new ArrayList<>();
			Set<String> playListSet = createPlayListSet(itemsNode);
			for (String s : playListSet) {
				PlayList playList = new PlayList();
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

	}*/

}
