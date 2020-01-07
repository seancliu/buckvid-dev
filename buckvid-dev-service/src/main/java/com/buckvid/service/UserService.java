package com.buckvid.service;

import com.buckvid.pojo.BuckvidUsers;

public interface UserService {


    /**
     * @param username
     * @return if username is existent
     */
    public boolean isUsernameExistent(String username);

    /**
     * @param user
     *  save user's info
     */
    public void saveUser(BuckvidUsers user);

    /**
     * @param username
     * @param password
     * @return matched user
     */
    public BuckvidUsers queryForSignIn(String username, String password);

    public void updateUserInfo(BuckvidUsers user);
}
