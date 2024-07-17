package com.todosalau.slicemaster.ui.main.utils;

import android.database.Cursor;

public class CursorUtils {
    public static Integer getInt(Cursor cursor, String columName) {
        int index = cursor.getColumnIndex(columName);
        if (index != -1) {
            return cursor.getInt(index);
        }
        return null;
    }

    public static String getString(Cursor cursor, String columName) {
        int index = cursor.getColumnIndex(columName);
        if (index != -1) {
            return cursor.getString(index);
        }
        return null;
    }
}
