package com.sahmyook.attendance.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AttendanceController {
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/")
    public String test() {return "/main";}

    @GetMapping("/admin")
    public String admin(){return "/admin";}

    @GetMapping("/login")
    public String login(){return "/login";}

    @GetMapping("/main")
    public String user(){return "/main";}

}
