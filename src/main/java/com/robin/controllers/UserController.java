package com.robin.controllers;

import com.robin.dao.UserDao;
import com.robin.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/create")
    @ResponseBody
    public String create(String userName, String email) {
        User user = null;

        try {
            user = new User(userName, email);
            userDao.save(user);
        } catch (Exception e) {
            return "Error creating user: " + e.getMessage();
        }
        return "User created successfully(id=" + user.getUserId() + ")";
    }

    @RequestMapping("/get-by-email")
    @ResponseBody
    public String getByEmail(String email) {
        User user;
        try {
            user = userDao.findByEmail(email);
        } catch (Exception ex) {
            return "User not found";
        }
        return "The user is: " + user;
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateUser(Long userId, String name, String email) {
        try {
            User user = userDao.findOne(userId);
            user.setEmail(email);
            user.setUserName(name);
            userDao.save(user);
        } catch (Exception ex) {
            return "Error updating the user: " + ex.toString();
        }
        return "User updated successfully!";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteUSer(long userId) {

        try {
            User user = new User(userId);
            userDao.delete(user);
        } catch (Exception e) {
            return "Error deleting user: " + e.getMessage();

        }
        return "User deleted successfully";
    }

    @RequestMapping("/getAllUsers")
    @ResponseBody
    public String getAllUsers() {

        List<User> users = new ArrayList<>();
        try {
            userDao.findAll().forEach(user -> users.add(user));


        } catch (Exception e) {
            return "Error retrieving users: " + e.getMessage();
        }
        return users.toString();
    }
}
