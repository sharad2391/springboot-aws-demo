package com.example.springboot_aws_demo.vo;

public class Video {

	private String videoId;
	private String video_Title;
	private String video_Description;

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideo_Title() {
		return video_Title;
	}

	public void setVideo_Title(String video_Title) {
		this.video_Title = video_Title;
	}

	public String getVideo_Description() {
		return video_Description;
	}

	public void setVideo_Description(String video_Description) {
		this.video_Description = video_Description;
	}

	@Override
	public String toString() {
		return "Video [videoId=" + videoId + ", video_Title=" + video_Title + ", video_Description=" + video_Description
				+ "]";
	}

}
