package com.visionary.crofting.service.Impl;

import com.visionary.crofting.dto.LoginDTO;
import com.visionary.crofting.entity.Stock;
import com.visionary.crofting.entity.User;
import com.visionary.crofting.repository.StockRepository;
import com.visionary.crofting.repository.UserRepository;
import com.visionary.crofting.service.IAuthService;
import com.visionary.crofting.util.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImp implements IAuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    StockRepository stockRepository;
    @Override
    public boolean isLoginInfoValid(LoginDTO login) {
        Optional<User> optionalUser=userRepository.findByEmail(login.getEmail());
        Optional<Stock> optionalStock=stockRepository.findStockByEmail(login.getEmail());

        return (optionalStock.isPresent() && PasswordHasher.verify(optionalStock.get().getPassword(),login.getPassword()))
                ||
                (optionalUser.isPresent() && PasswordHasher.verify(optionalUser.get().getPassword(),login.getPassword()));
    }
}
