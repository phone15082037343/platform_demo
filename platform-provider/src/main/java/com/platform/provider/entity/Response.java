package com.platform.provider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response implements Serializable {

    public static Integer SUCCESS = 200;

    /** 状态码 */
    private Integer code = 500;
    /** 提示消息 */
    private String message;
    /** 相应数据 */
    private Object data;

    public Response(String message) {
        this.message = message;
    }

    public Response(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

}
