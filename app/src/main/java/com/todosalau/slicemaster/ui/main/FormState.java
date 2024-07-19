package com.todosalau.slicemaster.ui.main;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class FormState {
    @Nullable
    private final Map<String, Integer> errors = new HashMap<>();

    public Boolean isValid() {
        assert errors != null;
        return errors.isEmpty();
    }

    public void addError(String reason, Integer code) {
        if (errors != null) {
            errors.put(reason, code);
        }
    }
}
