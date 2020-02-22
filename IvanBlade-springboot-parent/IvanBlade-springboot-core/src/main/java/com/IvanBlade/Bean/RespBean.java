package com.IvanBlade.Bean;

import lombok.Data;


/**
 * @param <T> 可变类型
 */
@Data
public class RespBean<T> {
    private boolean status;
    private String msg;
    private T detail;

    public RespBean() {
    }

    public RespBean(boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public RespBean(boolean status, String msg, T detail) {
        this.status = status;
        this.msg = msg;
        this.detail = detail;
    }
}
