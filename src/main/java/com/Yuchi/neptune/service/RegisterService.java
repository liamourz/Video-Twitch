package com.Yuchi.neptune.service;

import com.Yuchi.neptune.dao.RegisterDao;
import com.Yuchi.neptune.entity.db.User;
import com.Yuchi.neptune.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RegisterService {

    @Autowired
    private RegisterDao registerDao;

    public boolean register(User user) throws IOException {
        user.setPassword(Util.encryptPassword(user.getUserId(), user.getPassword()));
        return registerDao.register(user);
    }
}
