package com.slow.springbootburmawxlogin.controller;

import com.slow.springbootburmawxlogin.dto.UserLoginDTO;
import com.slow.springbootburmawxlogin.entity.User;
import com.slow.springbootburmawxlogin.properties.JwtProperties;
import com.slow.springbootburmawxlogin.service.UserLoginService;
import com.slow.springbootburmawxlogin.utils.JwtUtil;
import com.slow.springbootburmawxlogin.utils.Result;
import com.slow.springbootburmawxlogin.vo.UserLoginVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信相关接口
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class WxLoginController {
    @Autowired
    UserLoginService userLoginService;
    @Autowired
    JwtProperties jwtProperties;
    /**
     * 微信登录
     */
    @PostMapping("/user/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("微信登录授权码:{}",userLoginDTO.getCode());
        User user = userLoginService.wxLogin(userLoginDTO);
//        log.info("微信用户登录授权码：{}",user.getOpenid());
        Map<String, Object> claims=new HashMap<>();
        claims.put("userId",user.getId());
        String token = JwtUtil.createJWT(claims,jwtProperties.getUserTtl(),jwtProperties.getUserSecretKey());

        UserLoginVO userLoginVO = UserLoginVO.builder().id(user.getId()).openid(user.getOpenid()).token(token).build();
        log.info("userLoginVO:{}",userLoginVO);
        return Result.success(userLoginVO);
    }

}
