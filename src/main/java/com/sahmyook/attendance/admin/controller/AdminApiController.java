package com.sahmyook.attendance.admin.controller;

import com.sahmyook.attendance.admin.domain.AddUser;
import com.sahmyook.attendance.admin.domain.User;
import com.sahmyook.attendance.admin.service.UserService;
import com.sahmyook.attendance.global.domain.ResultCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    @PostMapping("/login")
    public ResultCode loginUser(@ModelAttribute User loginUser){
        return userService.login(loginUser);
    }

    @PutMapping("/change")
    public ResultCode changeStatus(@ModelAttribute User user){
        return userService.changeStatus(user);
    }
}
