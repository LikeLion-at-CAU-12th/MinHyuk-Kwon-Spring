package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Controller
@RestController
public class TestController {
    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    public String test() {
        return "hello spring! this is test1 by RequestMapping with RequestMethod.GET";
    }

    @GetMapping(value = "/test2")
    public String test2() {
        return "hello spring! this is test2 by GetMapping";
    }
}
