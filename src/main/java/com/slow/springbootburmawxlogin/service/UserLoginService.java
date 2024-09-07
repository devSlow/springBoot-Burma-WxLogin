package com.slow.springbootburmawxlogin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.slow.springbootburmawxlogin.dto.UserLoginDTO;
import com.slow.springbootburmawxlogin.entity.User;


public interface UserLoginService extends IService<User> {
    /**
     * 微信登录
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
