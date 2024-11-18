/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.UserAccountManagement;

import model.Personnel.Profile;

/**
 *
 * @author kal bugrara
 */
public class UserAccount {

    private Profile profile;
    private String username;
    private String password;

    public UserAccount(Profile profile, String username, String password) {
        this.profile = profile;
        this.username = username;
        this.password = password;
    }

    public String getPersonId() {
        return profile.getPerson().getPersonId();
    }

    public boolean isMatch(String id) {
        return getPersonId().equals(id);
    }

    public boolean isValidUser(String username, String password) {
        return this.username.equalsIgnoreCase(username) && this.password.equals(password);
    }

    public String getRole() {
        return profile.getRole();
    }

    public Profile getAssociatedPersonProfile() {
        return profile;
    }
}
