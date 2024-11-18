/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.UserAccountManagement;

import java.util.ArrayList;

import model.Personnel.*;

/**
 *
 * @author kal bugrara
 */
public class UserAccountDirectory {

    private ArrayList<UserAccount> userAccountList;

    public UserAccountDirectory() {
        this.userAccountList = new ArrayList<>();
    }

    public UserAccount newUserAccount(Profile profile, String username, String password) {
        UserAccount userAccount = new UserAccount(profile, username, password);
        userAccountList.add(userAccount);
        return userAccount;
    }

    public UserAccount findUserAccount(String id) {
        for (UserAccount userAccount : userAccountList) {
            if (userAccount.isMatch(id)) {
                return userAccount;
            }
        }
        return null; 
    }

    public UserAccount authenticateUser(String username, String password) {
        for (UserAccount userAccount : userAccountList) {
            if (userAccount.isValidUser(username, password)) {
                return userAccount;
            }
        }
        return null; 
    }

    public ArrayList<UserAccount> getUserAccountList() {
        return userAccountList;
    }
}