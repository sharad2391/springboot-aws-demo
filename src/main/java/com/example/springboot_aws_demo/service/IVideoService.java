package com.example.springboot_aws_demo.service;

import java.util.Collection;

import com.example.springboot_aws_demo.vo.PlayList;
import com.example.springboot_aws_demo.vo.Video;

public interface IVideoService {

	public Collection<PlayList> getPlayListDetails() throws Exception ;

	public Collection<Video> getVideoList();
}
