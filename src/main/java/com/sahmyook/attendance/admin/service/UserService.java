package com.sahmyook.attendance.admin.service;

import com.sahmyook.attendance.admin.dao.UserRepository;
import com.sahmyook.attendance.admin.domain.AddUser;
import com.sahmyook.attendance.admin.domain.User;
import com.sahmyook.attendance.global.domain.ResultCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> getAll(){return userRepository.findAll();}

    public ResultCode save(AddUser addUser){
        if(userRepository.existsByUserId(addUser.getUserId())){
            return new ResultCode(-99, "중복된 아이디 입니다.");
        }else {
            User user = new User();
            user.setUserId(addUser.getUserId());
            user.setUserPw(addUser.getUserPw());
            user.setStatus(addUser.getStatus());

            userRepository.save(user);
            return new ResultCode(0, "새 아이디를 생성하였습니다.");
        }
    }

    public ResultCode login(User loginUser){
        if(userRepository.existsByUserId(loginUser.getUserId())){
            User users = userRepository.findByUserId(loginUser.getUserId());
            if(users.getUserPw().equals(loginUser.getUserPw())){
                return new ResultCode(0, "로그인 성공!");
            }
            else{
                return new ResultCode(-99, "비밀번호가 틀립니다.");
            }
        }else {
            return new ResultCode(-99, "존재하지 않는 아이디입니다.");
        }
    }

    public ResultCode changeStatus(User onUser) {
        if (userRepository.existsByUserId(onUser.getUserId())) {
            User user = userRepository.findByUserId(onUser.getUserId());
            if (user.getStatus()==1) user.setStatus(0);
            else user.setStatus(1);
            userRepository.save(user);
            return new ResultCode(0, "출근 성공!");
        } else {
            return new ResultCode(-99, "존재하지 않는 아이디입니다.");
        }
    }
}
