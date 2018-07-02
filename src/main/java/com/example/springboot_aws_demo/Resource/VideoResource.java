package com.example.springboot_aws_demo.Resource;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot_aws_demo.service.IVideoService;
import com.example.springboot_aws_demo.service.impl.VideoServiceImpl;
import com.example.springboot_aws_demo.vo.PlayList;
import com.example.springboot_aws_demo.vo.Video;

@RestController
public class VideoResource {

	/*@Autowired
	QuizDao quizDao;*/
	
	@Autowired
	IVideoService videoService;
	
	/*@RequestMapping(value = "/quiz")
	public List<Quiz> getQuizData() {
		
		return (List<Quiz>) quizDao.findAll();
		//return "Greetings from Spring Boot!";
	}*/
	
	@GetMapping(value = "/playList")
	public Collection<PlayList> getPlayList() throws Exception {
		return videoService.getPlayListDetails();
		
	}
	
	@GetMapping(value = "playList/{playListId}",consumes = "application/json")
	public Collection<Video> getVideos(@PathVariable("playListId") String playListId) throws Exception {
		System.out.println("PlayList Id is::"+playListId);
		return videoService.getVideoList(playListId);
		
	}
}
