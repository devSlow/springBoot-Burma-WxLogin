package com.slow.springbootburmawxlogin.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slow.springbootburmawxlogin.constant.MessageConstant;
import com.slow.springbootburmawxlogin.dto.UserLoginDTO;
import com.slow.springbootburmawxlogin.entity.User;
import com.slow.springbootburmawxlogin.exception.LoginFailedException;
import com.slow.springbootburmawxlogin.mapper.UserLoginMapper;
import com.slow.springbootburmawxlogin.properties.WeChatProperties;
import com.slow.springbootburmawxlogin.service.UserLoginService;
import com.slow.springbootburmawxlogin.utils.HttpClientUtil;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

/**
 * @author slow
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-09-06 18:52:09
 */
@Service
@Slf4j
public class UserLoginServiceImpl extends ServiceImpl<UserLoginMapper, User>
        implements UserLoginService {
    @Autowired
    private WeChatProperties weChatProperties;
    @Resource
    private UserLoginMapper userLoginMapper;
    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 微信登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", userLoginDTO.getCode());
        map.put("grant_type", "authorization_code");
//        log.info("微信登录授权码:{}",userLoginDTO.getCode());
        log.info("微信登录请求参数:{}",map);
        String json = HttpClientUtil.doGet(WX_LOGIN_URL, map);
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        if (openid == null) {
            log.info("微信登录失败:{}",openid);
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
//        判断是否是新用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        User user = userLoginMapper.selectOne(queryWrapper);
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .build();
            userLoginMapper.insert(user);
        }
        return user;
    }
}




