package com.todosalau.slicemaster.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SliceMaster";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATION_QUERY = "CREATE TABLE " + ProductEntity.TABLE_NAME
            + " ( "
            + ProductEntity.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ProductEntity.NAME + " TEXT, "
            + ProductEntity.INGREDIENTS + " TEXT, "
            + ProductEntity.PRICE + " INTEGER,"
            + ProductEntity.SIZE + " INTEGER,"
            + ProductEntity.ENABLE + " INTEGER)";

    private final ProductDao productDao = new ProductDao(this);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATION_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ProductEntity.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }


}
