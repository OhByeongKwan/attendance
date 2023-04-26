package com.sahmyook.attendance.admin.service;

import com.sahmyook.attendance.admin.dao.UserRepository;
import com.sahmyook.attendance.admin.domain.AddUser;
import com.sahmyook.attendance.admin.domain.EditUser;
import com.sahmyook.attendance.admin.domain.User;
import com.sahmyook.attendance.global.domain.ResultCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public List<User> getAll(){return userRepository.findAll();}

    public ResultCode save(AddUser addUser){
        if(addUser ==null || addUser.getUserId().isEmpty()) {
            return new ResultCode(-99, "유효하지 않은 입력입니다.");
        }
        if(userRepository.existsByUserId(addUser.getUserId())){

            return new ResultCode(-99, "중복된 아이디 입니다.");
        }
        else {

            User user = new User();

            user.setUserId(addUser.getUserId());

            String rawPassword = "0000";

            user.setUserPw(bCryptPasswordEncoder.encode(rawPassword));
            user.setStatus(addUser.getStatus());
            user.setRole("ROLE_USER");
            user.setUserName(addUser.getUserId());

            userRepository.save(user);
            return new ResultCode(0, "새 아이디를 생성하였습니다.");
        }
    }


    public ResultCode on(String userId) {
        if (userRepository.existsByUserId(userId)) {
            User user = userRepository.findByUserId(userId);
            if (user.getStatus()==1) return new ResultCode(-99, "이미 출근한 상태입니다.",user.getStatus());
            else {
                user.setStatus(1);
                userRepository.save(user);
                return new ResultCode(0, "출근을 완료하였습니다.",user.getStatus());
            }
        } else {
            return new ResultCode(-99, "존재하지 않는 아이디입니다. 관리자에게 문의해주세요.");
        }
    }

    public ResultCode off(String userId) {
        if (userRepository.existsByUserId(userId)) {
            User user = userRepository.findByUserId(userId);

            if (user.getStatus()==0) return new ResultCode(-99, "이미 퇴근한 상태입니다.",user.getStatus());
            else {
                user.setStatus(0);
                userRepository.save(user);
                return new ResultCode(0, "퇴근을 완료하였습니다.",user.getStatus());
            }
        } else {
            return new ResultCode(-99, "존재하지 않는 아이디입니다. 관리자에게 문의해주세요.");
        }
    }

    public Integer getStatus(String userId) {
        if (userRepository.existsByUserId(userId)) {
            User user = userRepository.findByUserId(userId);
            System.out.println(user.getStatus());
            return user.getStatus();
        } else {
            return -99;
        }
    }

    public User getOne(String userId) {
        if (userRepository.existsByUserId(userId)) {
            User user = userRepository.findByUserId(userId);
            System.out.println(user.getUserId());
            return user;
        } else {
            return null;
        }
    }

    public ResultCode edit(EditUser editUser) {
        Optional<User> user = userRepository.findById(editUser.getId());

        if(user.isPresent()) {
            User oldUser = user.get();
            oldUser.setUserId(editUser.getUserId());

            String rawPassword = editUser.getUserPw();
            oldUser.setUserPw(bCryptPasswordEncoder.encode(rawPassword));

            oldUser.setUserName(editUser.getUserName());

            return new ResultCode(0, "회원 정보를 수정했습니다.");
        }
        else {
            return new ResultCode(-99, "에러");
        }
    }
}
