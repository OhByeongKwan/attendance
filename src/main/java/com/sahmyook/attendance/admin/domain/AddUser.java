package com.sahmyook.attendance.admin.domain;

import jakarta.persistence.GeneratedValue;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@DynamicInsert
public class AddUser {
    private String userId;
    private Integer status;
}
