package com.todosalau.slicemaster.data;

import com.todosalau.slicemaster.data.model.LoggedInUser;

public interface FirebaseCallback {
    void onSuccess(LoggedInUser result);

    void onError(Exception error);
}
