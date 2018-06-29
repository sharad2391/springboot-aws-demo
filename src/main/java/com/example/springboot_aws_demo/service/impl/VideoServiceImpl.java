package com.example.springboot_aws_demo.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.springboot_aws_demo.service.IVideoService;
import com.example.springboot_aws_demo.vo.PlayList;
import com.example.springboot_aws_demo.vo.Video;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class VideoServiceImpl implements IVideoService {

	@Autowired
	RestTemplate restTemplate;

	@Override
	public Collection<PlayList> getPlayListDetails() throws Exception {
		System.out.println("Creating playlist list from playlistYoutubeAPIService");
		Collection<PlayList> playListCollection = new ArrayList<>();

		String playListBaseUrl = "https://www.googleapis.com/youtube/v3/playlists";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(playListBaseUrl).queryParam("part", "snippet")
				.queryParam("channelId", "UC59K-uG2A5ogwIrHw4bmlEg")
				.queryParam("key", "AIzaSyAswyWngcFEjCF8-CkRhmCNPH-7esa24r0").queryParam("maxResults", "50");

		System.out.println("Url for playlist is without token::" + builder.toUriString());

		ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			String playListJson = response.getBody();
			System.out.println("Result fetched from Youtube");
			System.out.println(playListJson);

			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode;
			try {
				rootNode = mapper.readTree(playListJson);
				getPlayListSet(playListJson, playListCollection, rootNode);

				while (!rootNode.path("nextPageToken").isMissingNode()) {
					String nextPageToken = rootNode.path("nextPageToken").asText();
					UriComponentsBuilder builder1 = UriComponentsBuilder.fromUriString(playListBaseUrl)
							.queryParam("part", "snippet").queryParam("channelId", "UC59K-uG2A5ogwIrHw4bmlEg")
							.queryParam("key", "AIzaSyAswyWngcFEjCF8-CkRhmCNPH-7esa24r0").queryParam("maxResults", "50")
							.queryParam("pageToken", nextPageToken);
					System.out.println("Url for playlist is with next page token::" + builder.toUriString());
					ResponseEntity<String> response1 = restTemplate.getForEntity(builder1.toUriString(), String.class);

					System.out.println("Result fetched from Youtube");
					System.out.println(response1.getBody());

					rootNode = mapper.readTree(response1.getBody());
					getPlayListSet(response1.getBody(), playListCollection, rootNode);
				}

			} catch (IOException e) {
				System.err.println("Error in mapping jsonString into Json Node" + e);
			}
		} else {
			System.err.println("Youtube playlistService service is not up");
			throw new Exception("Youtube playlistService service is not up");
		}
		System.out.println("Playlist created*****");
		return playListCollection;
	}

	/**
	 * 
	 * @param playListJson
	 * @param playListCollection
	 * @param rootNode
	 */
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

	@Override
	public Collection<Video> getVideoList() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * private Collection<PlayList> processYoutubeJson(String youtubeJson) {
	 * 
	 * Collection<PlayList> playListCollection = new ArrayList<>(); ObjectMapper
	 * mapper = new ObjectMapper();
	 * 
	 * try { JsonNode rootNode = mapper.readTree(youtubeJson); JsonNode itemsNode =
	 * rootNode.path("items");
	 * 
	 * 
	 * Collection<Video> videosList = new ArrayList<>(); Set<String> playListSet =
	 * createPlayListSet(itemsNode); for (String s : playListSet) { PlayList
	 * playList = new PlayList(); playList.setPlaylistId(s);
	 * 
	 * for (JsonNode items : itemsNode) {
	 * 
	 * Video video = new Video(); JsonNode snippetNode = items.path("snippet"); if
	 * (!snippetNode.isMissingNode()) {
	 * video.setVideo_Title(snippetNode.path("title").asText());
	 * video.setVideo_Description(snippetNode.path("description").asText()); }
	 * 
	 * JsonNode contentDetailsNode = items.path("contentDetails"); JsonNode
	 * playlistItem = contentDetailsNode.path("playlistItem"); if
	 * (!playlistItem.isMissingNode()) { JsonNode resourceId =
	 * playlistItem.path("resourceId");
	 * video.setVideoId(resourceId.path("videoId").asText());
	 * 
	 * if (playlistItem.path("playlistId").asText().equalsIgnoreCase(s)) {
	 * videosList.add(video); }
	 * 
	 * } } playList.setVideos(videosList); playListCollection.add(playList); }
	 * 
	 * 
	 * 
	 * } catch (IOException e) {
	 * System.out.println("Error in mappiing youtubeJson into tree of Json Nodes");
	 * e.printStackTrace(); } return playListCollection; }
	 * 
	 * private Set<String> createPlayListSet(JsonNode itemsNode) { Set<String>
	 * playListSet = new HashSet<>(); for (JsonNode items : itemsNode) {
	 * 
	 * JsonNode contentDetailsNode = items.path("contentDetails"); JsonNode
	 * playlistItem = contentDetailsNode.path("playlistItem"); if
	 * (!playlistItem.isMissingNode()) {
	 * playListSet.add(playlistItem.path("playlistId").asText());
	 * 
	 * } } return playListSet;
	 * 
	 * }
	 */

}
