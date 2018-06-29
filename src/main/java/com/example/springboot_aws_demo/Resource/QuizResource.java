package com.example.springboot_aws_demo.Resource;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot_aws_demo.service.IVideoService;
import com.example.springboot_aws_demo.service.impl.VideoServiceImpl;
import com.example.springboot_aws_demo.vo.PlayList;
import com.example.springboot_aws_demo.vo.Video;

@RestController
public class QuizResource {

	/*@Autowired
	QuizDao quizDao;*/
	
	@Autowired
	IVideoService videoService;
	
	/*@RequestMapping(value = "/quiz")
	public List<Quiz> getQuizData() {
		
		return (List<Quiz>) quizDao.findAll();
		//return "Greetings from Spring Boot!";
	}*/
	
	@RequestMapping(value = "/playList")
	public Collection<PlayList> getPlayList() throws Exception {
		return videoService.getPlayListDetails();
		
	}
	
	@GetMapping(value = "/videos")
	public Collection<Video> getVideos() throws Exception {
		return videoService.getVideoList();
		
	}
}
