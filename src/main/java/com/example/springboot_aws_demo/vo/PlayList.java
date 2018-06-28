package com.example.springboot_aws_demo.vo;

public class PlayList {

	private String playlistId;
	private String title;
	public String getPlaylistId() {
		return playlistId;
	}
	public void setPlaylistId(String playlistId) {
		this.playlistId = playlistId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "PlayListDetails [playlistId=" + playlistId + ", title=" + title + "]";
	}

	
}
