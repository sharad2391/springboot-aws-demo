package com.example.springboot_aws_demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.springboot_aws_demo.vo.Quiz;
@Component
@Repository
public interface QuizDao extends CrudRepository<Quiz, Integer> {

}
