package com.example.springboot_aws_demo.Resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot_aws_demo.dao.QuizDao;
import com.example.springboot_aws_demo.vo.Quiz;

@RestController
public class QuizResource {

	@Autowired
	QuizDao quizDao;
	
	@RequestMapping(value = "/quiz")
	public List<Quiz> getQuizData() {
		
		return (List<Quiz>) quizDao.findAll();
		//return "Greetings from Spring Boot!";
	}
}
