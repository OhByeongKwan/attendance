package com.sahmyook.attendance.global.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class ResultCode {
    private int code = -99;
    private String message = "예기치 않은 에러입니다.";
    private Object data = null;

    public ResultCode(int code, String message) {
        this(code, message, null);
    }

    public ResultCode(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultCode(int code) {
        this.code = code;
        if(code == 0 ) {
            this.message = "성공적으로 처리하였습니다.";
        }
    }

    public ResultCode data(Object data) {
        this.data = data;
        return this;
    }

    public ResultCode(Exception e) {
        code = -89;
        message = e.getLocalizedMessage();
        e.printStackTrace();
    }
}
