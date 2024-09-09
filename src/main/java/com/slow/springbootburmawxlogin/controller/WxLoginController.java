package com.slow.springbootburmawxlogin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.slow.springbootburmawxlogin.dto.UserLoginDTO;
import com.slow.springbootburmawxlogin.entity.User;
import com.slow.springbootburmawxlogin.properties.JwtProperties;
import com.slow.springbootburmawxlogin.service.UserLoginService;
import com.slow.springbootburmawxlogin.utils.JwtUtil;
import com.slow.springbootburmawxlogin.utils.Result;
import com.slow.springbootburmawxlogin.vo.UserLoginVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
     * 微信登录 当新用户登录时新增到数据库
     */
    @PostMapping("/user/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("微信登录授权码:{}",userLoginDTO.getCode());
        log.info("微信登录参数:{}",userLoginDTO);
        User user = userLoginService.wxLogin(userLoginDTO);
//        log.info("微信用户登录授权码：{}",user.getOpenid());
        Map<String, Object> claims=new HashMap<>();
        claims.put("userId",user.getId());
        String token = JwtUtil.createJWT(claims,jwtProperties.getUserTtl(),jwtProperties.getUserSecretKey());

        UserLoginVO userLoginVO = UserLoginVO.builder().id(user.getId()).openid(user.getOpenid()).token(token).build();
        log.info("userLoginVO:{}",userLoginVO);
        return Result.success(userLoginVO);
    }
    /**
     * 根据id删除用户
     */
    @DeleteMapping("/user/{id}")
    public Result delete(@PathVariable Long id) {
        userLoginService.removeById(id);
        return Result.success("删除成功");
    }
    /**
     * 修改用户信息
     */
    @PutMapping("/user/{id}")
    public Result update(@PathVariable Long id, @RequestBody User user) {

        userLoginService.updateUserById(id, user);
        return Result.success();
    }
    /**
     * 根据id查询用户信息
     */
    @GetMapping("/user/{id}")
    public Result<User> getById(@PathVariable Long id) {
        User user = userLoginService.getById(id);
        return Result.success(user);
    }

    /**
     * 查询所有用户信息
     */
    @GetMapping("/user")
    public Result<List<User>> getList() {
        return Result.success(userLoginService.list());
    }
}
