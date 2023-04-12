package com.sahmyook.attendance.admin.controller;

import com.sahmyook.attendance.admin.domain.AddUser;
import com.sahmyook.attendance.admin.domain.User;
import com.sahmyook.attendance.admin.service.UserService;
import com.sahmyook.attendance.global.domain.ResultCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/admins")
@AllArgsConstructor
@RestController
public class AdminApiController {
    private final UserService userService;

    @GetMapping("")
        public List<User> getAll(){return userService.getAll();}

    @PostMapping("/add")
        public ResultCode addUser(@ModelAttribute AddUser addUser){
        return userService.save(addUser);
        }

    @PutMapping("/on")
    public ResultCode on(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;
        String userId = ((UserDetails) principal).getUsername();
        return userService.on(userId);
    }

    @PutMapping("/off")
    public ResultCode off(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;
        String userId = ((UserDetails) principal).getUsername();
        return userService.off(userId);
    }
}
