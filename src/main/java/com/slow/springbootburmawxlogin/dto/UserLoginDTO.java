package com.slow.springbootburmawxlogin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginDTO implements Serializable {
    String code;
}
