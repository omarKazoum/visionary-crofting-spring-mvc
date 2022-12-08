package com.visionary.crofting.dto;

import com.visionary.crofting.validation.ValidRoleId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class LoginDTO {
    @ValidRoleId
    long roleId;
    @NotBlank(message = "email cannot be empty!")
    String email;
    @NotBlank(message = "please enter a password!")
    String password;
    boolean rememberMe;

}
