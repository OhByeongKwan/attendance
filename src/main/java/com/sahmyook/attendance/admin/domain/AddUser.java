package com.sahmyook.attendance.admin.domain;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@DynamicInsert
public class AddUser {
    private String userId;
    private String userPw;
    private Integer status;
}
