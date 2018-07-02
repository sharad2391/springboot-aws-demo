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
				createPlayList(playListJson, playListCollection, rootNode);

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
					createPlayList(response1.getBody(), playListCollection, rootNode);
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
	private void createPlayList(String playListJson, Collection<PlayList> playListCollection, JsonNode rootNode) {

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
	public Collection<Video> getVideoList(String playListId) throws Exception {
		System.out.println("Creating videos list from videolistYoutubeAPIService for playlist with id::" + playListId);

		Collection<Video> videosList = new ArrayList<>();

		String playListItemsUrl = "https://www.googleapis.com/youtube/v3/playlistItems";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(playListItemsUrl)
				.queryParam("part", "snippet,contentDetails").queryParam("playlistId", playListId)
				.queryParam("key", "AIzaSyAswyWngcFEjCF8-CkRhmCNPH-7esa24r0").queryParam("maxResults", "50");
		System.out.println("Url for videolist is without token::" + builder.toUriString());

		ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			String videoListJson = response.getBody();
			System.out.println("Result fetched from Youtube");
			System.out.println(videoListJson);

			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode;
			try {
				rootNode = mapper.readTree(videoListJson);
				createVideoList(videoListJson, videosList, rootNode);

				while (!rootNode.path("nextPageToken").isMissingNode()) {
					String nextPageToken = rootNode.path("nextPageToken").asText();
					UriComponentsBuilder builder1 = UriComponentsBuilder.fromUriString(playListItemsUrl)
							.queryParam("part", "snippet,contentDetails").queryParam("playlistId", playListId)
							.queryParam("key", "AIzaSyAswyWngcFEjCF8-CkRhmCNPH-7esa24r0").queryParam("maxResults", "50")
							.queryParam("pageToken", nextPageToken);

					System.out.println("Url for videoList is with next page token::" + builder1.toUriString());
					ResponseEntity<String> response1 = restTemplate.getForEntity(builder1.toUriString(), String.class);

					System.out.println("Result fetched from Youtube");
					System.out.println(response1.getBody());

					rootNode = mapper.readTree(response1.getBody());
					createVideoList(response1.getBody(), videosList, rootNode);
				}

			} catch (IOException e) {
				System.err.println("Error in mapping jsonString into Json Node" + e);
			}
		} else {
			System.err.println("Youtube videolistService service is not up");
			throw new Exception("Youtube videolistService service is not up");
		}
		System.out.println("Videolist created*****");
		return videosList;

	}

	/**
	 * 
	 * @param playListJson
	 * @param videosList
	 * @param rootNode
	 */
	private void createVideoList(String playListJson, Collection<Video> videosList, JsonNode rootNode) {

		JsonNode itemsNode = rootNode.path("items");

		for (JsonNode node : itemsNode) {
			Video video = new Video();
			JsonNode contentDetailsNode = node.path("contentDetails");
			if (!contentDetailsNode.isMissingNode()) {
				video.setVideoId(contentDetailsNode.path("videoId").asText());
			}

			JsonNode snippetNode = node.path("snippet");
			if (!snippetNode.isMissingNode()) {
				video.setVideo_Title(snippetNode.path("title").asText());
				video.setVideo_Description(snippetNode.path("description").asText());
			}
			videosList.add(video);
		}

	}

}
