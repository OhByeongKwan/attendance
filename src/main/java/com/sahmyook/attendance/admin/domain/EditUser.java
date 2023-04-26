package com.sahmyook.attendance.admin.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

@Data
@DynamicInsert
public class EditUser {
    private Long id;
    private String userId;
    private String userName;
    private String userPw;
}
