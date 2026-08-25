package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.vo.UserLoginVO;
/**
 * 用户相关业务接口
 */
public interface UserService {
    User wxLogin(UserLoginDTO userLoginDTO);

}
