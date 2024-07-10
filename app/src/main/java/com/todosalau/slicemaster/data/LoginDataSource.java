package com.todosalau.slicemaster.data;

import com.google.firebase.auth.FirebaseAuth;
import com.todosalau.slicemaster.data.model.LoggedInUser;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();

    public void login(String username, String password, FirebaseCallback callback) {
        try {
            fbAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            callback.onSuccess(new LoggedInUser(username, ""));
                        } else {
                            callback.onError(task.getException());
                        }
                    });
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    public void register(String username, String password, String displayName, FirebaseCallback callback) {
        try {
            fbAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            callback.onSuccess(new LoggedInUser(username, displayName));
                            //registrar datos adicionales
                        } else {
                            callback.onError(task.getException());
                        }
                    });
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}