package com.example.springboot_aws_demo.vo;

import java.util.Collection;

public class PlayListDetails {

	private String playlistId;
	private Collection<Video> videos;

	public String getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(String playlistId) {
		this.playlistId = playlistId;
	}

	public Collection<Video> getVideos() {
		return videos;
	}

	public void setVideos(Collection<Video> videos) {
		this.videos = videos;
	}

	@Override
	public String toString() {
		return "VideosData [playlistId=" + playlistId + ", videos=" + videos + "]";
	}

}
