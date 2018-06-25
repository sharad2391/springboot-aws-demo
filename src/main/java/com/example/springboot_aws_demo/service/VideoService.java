package com.example.springboot_aws_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class VideoService {

	@Autowired
	RestTemplate restTemplate;

	public String getDataFromYoutubeService() {
		final String uri = "https://www.googleapis.com/youtube/v3/activities?part=snippet,contentDetails&channelId=UC59K-uG2A5ogwIrHw4bmlEg&key=AIzaSyAswyWngcFEjCF8-CkRhmCNPH-7esa24r0";
		String result = restTemplate.getForObject(uri, String.class);
		
		System.out.println("Result fetched from Youtube");
		System.out.println(result);
		return result;
	}

}
