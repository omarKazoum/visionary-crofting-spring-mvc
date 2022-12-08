package com.visionary.crofting.service;

import com.visionary.crofting.dto.LoginDTO;

public interface IAuthService {
    boolean isLoginInfoValid(LoginDTO login);
}
