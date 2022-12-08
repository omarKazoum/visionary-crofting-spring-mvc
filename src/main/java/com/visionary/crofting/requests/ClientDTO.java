package com.visionary.crofting.requests;


import lombok.Data;

@Data
public class ClientDTO {
    private String uuid;
    private String name;
    private String email;
    private String password;
    private String phone;
}
