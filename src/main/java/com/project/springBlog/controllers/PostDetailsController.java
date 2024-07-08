package com.project.springBlog.controllers;

import com.project.springBlog.services.PostDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postDetails")
public class PostDetailsController {
    @Autowired
    PostDetailsService postDetailsService;


}
