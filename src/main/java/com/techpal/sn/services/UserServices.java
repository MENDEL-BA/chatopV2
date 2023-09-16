package com.techpal.sn.services;

import com.techpal.sn.controllers.UserInfoResponse;
import com.techpal.sn.dto.LoginDto;
import com.techpal.sn.dto.RegisterDto;
import com.techpal.sn.payload.response.AuthSuccess;

public interface UserServices {

     AuthSuccess register(RegisterDto registerDto);

     UserInfoResponse getUser();

     AuthSuccess loggedIn(LoginDto loginDto);
}
