package com.slow.springbootburmawxlogin.dto;

import com.slow.springbootburmawxlogin.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginDTO extends User implements Serializable {
    String code;
}
