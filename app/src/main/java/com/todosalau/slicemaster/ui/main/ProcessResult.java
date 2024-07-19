package com.todosalau.slicemaster.ui.main;

import androidx.annotation.Nullable;

import java.util.List;

public class ProcessResult {
    @Nullable
    private List<Long> modifiedData;
    @Nullable
    private Integer error;

    public ProcessResult(@Nullable List<Long> modifiedData) {
        this.modifiedData = modifiedData;
    }

    public ProcessResult(@Nullable Integer error) {
        this.error = error;
    }

    @Nullable
    public List<Long> getModifiedData() {
        return modifiedData;
    }

    public void setModifiedData(@Nullable List<Long> modifiedData) {
        this.modifiedData = modifiedData;
    }

    @Nullable
    public Integer getError() {
        return error;
    }

    public void setError(@Nullable Integer error) {
        this.error = error;
    }
}
