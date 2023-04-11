package com.sahmyook.attendance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AttendanceController {
    @GetMapping("/")
    public String test() {return "/admin";}

    @GetMapping("/admin")
    public String admin(){return "/admin";}



}
