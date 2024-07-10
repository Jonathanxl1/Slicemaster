package com.todosalau.slicemaster.data;

import com.todosalau.slicemaster.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;
    private LoggedInUser user = null;

    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    public void setLoggedInUser(LoggedInUser user) {
        this.user = user;
    }

    public void login(String username, String password, FirebaseCallback callback) {
        dataSource.login(username, password, callback);
    }

    public void register(String username, String password, String displayName, FirebaseCallback callback) {
        dataSource.register(username, password, displayName, callback);
    }

}