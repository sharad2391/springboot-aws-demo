package com.example.springboot_aws_demo.Resource;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot_aws_demo.service.VideoService;
import com.example.springboot_aws_demo.vo.PlayList;

@RestController
public class QuizResource {

	/*@Autowired
	QuizDao quizDao;*/
	
	@Autowired
	VideoService videoService;
	
	/*@RequestMapping(value = "/quiz")
	public List<Quiz> getQuizData() {
		
		return (List<Quiz>) quizDao.findAll();
		//return "Greetings from Spring Boot!";
	}*/
	
	@RequestMapping(value = "/videos")
	public Collection<PlayList> getVideosData() {
		return videoService.getPlayListDetailsFromYoutubeService();
		
		
		//return "Greetings from Spring Boot!";
	}
}
