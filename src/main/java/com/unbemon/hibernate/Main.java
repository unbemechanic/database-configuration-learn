package com.unbemon.hibernate;

import dao.DaoHibernate;
import dao.UserDao;
import model.User;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        UserDao userDao = new DaoHibernate();
        userDao.createUsersTable();
        userDao.saveUser("John", "Wick", (byte) 30);

        for(User user : userDao.getAllUsers()) {
            System.out.println(user);
        }
    }
}