package com.slow.springbootburmawxlogin.vo;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
@Builder
@Data
public class UserLoginVO implements Serializable {
    private Long id;
    private String openid;
    private String token;
}
