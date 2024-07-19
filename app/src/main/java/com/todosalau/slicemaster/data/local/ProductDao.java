package com.todosalau.slicemaster.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.todosalau.slicemaster.ui.main.Pizza;
import com.todosalau.slicemaster.ui.main.Size;
import com.todosalau.slicemaster.ui.main.utils.CursorUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    DatabaseHelper databaseHelper;


    public ProductDao(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public Pizza insert(Pizza pizza) throws Exception {
        SQLiteDatabase db = databaseHelper.open();
        try {
            ContentValues values = new ContentValues();
            values.put(ProductEntity.INGREDIENTS, pizza.getIngredients());
            values.put(ProductEntity.PRICE, pizza.getPrice());
            values.put(ProductEntity.SIZE, pizza.getSize().ordinal());
            values.put(ProductEntity.NAME, pizza.getName());
            values.put(ProductEntity.ENABLE, true);
            Long id = db.insert(ProductEntity.TABLE_NAME, null, values);
            pizza.setId(id);
            return pizza;
        } catch (Exception ex) {
            throw new Exception("Problema al obtener el listado de pizzas");
        } finally {
            db.close();
        }
    }

    public List<Pizza> getAll() throws Exception {
        SQLiteDatabase db = databaseHelper.open();
        List<Pizza> products = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + ProductEntity.TABLE_NAME + " WHERE " + ProductEntity.ENABLE + " = 1 ", null); // Ejecución de la consulta SQL
            if (cursor.moveToFirst()) {
                do {
                    Pizza product = new Pizza();
                    product.setId(CursorUtils.getInt(cursor, ProductEntity.ID));
                    product.setName(CursorUtils.getString(cursor, ProductEntity.NAME));
                    product.setIngredients(CursorUtils.getString(cursor, ProductEntity.INGREDIENTS));
                    product.setPrice(CursorUtils.getInt(cursor, ProductEntity.PRICE));
                    product.setSize(Size.values()[CursorUtils.getInt(cursor, ProductEntity.SIZE)]);
                    products.add(product);
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            throw new Exception("Problema al obtener el listado de pizzas");
        } finally {
            db.close();
            if (cursor != null) cursor.close();

        }
        return products;
    }

    public void update(Pizza pizza) throws Exception {
        SQLiteDatabase db = databaseHelper.open();
        try {
            ContentValues values = new ContentValues();
            values.put(ProductEntity.INGREDIENTS, pizza.getIngredients());
            values.put(ProductEntity.PRICE, pizza.getPrice());
            values.put(ProductEntity.SIZE, pizza.getSize().ordinal());
            values.put(ProductEntity.NAME, pizza.getName());
            values.put(ProductEntity.ENABLE, true);
            db.update(ProductEntity.TABLE_NAME, values, ProductEntity.ID + " = ?", new String[]{String.valueOf(pizza.getId())});
        } catch (Exception ex) {
            throw new Exception("Problema al obtener el listado de pizzas");
        } finally {
            db.close();
        }
    }

    public Pizza getById(Long id) throws Exception {
        SQLiteDatabase db = databaseHelper.open();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM "
                    + ProductEntity.TABLE_NAME
                    + " WHERE "
                    + ProductEntity.ID +
                    "=?", new String[]{String.valueOf(id)});
            Pizza product = new Pizza();
            if (cursor.moveToFirst()) {
                do {
                    product.setId(CursorUtils.getInt(cursor, ProductEntity.ID));
                    product.setName(CursorUtils.getString(cursor, ProductEntity.NAME));
                    product.setIngredients(CursorUtils.getString(cursor, ProductEntity.INGREDIENTS));
                    product.setPrice(CursorUtils.getInt(cursor, ProductEntity.PRICE));
                    product.setSize(Size.values()[CursorUtils.getInt(cursor, ProductEntity.SIZE)]);
                } while (cursor.moveToNext());
            }
            return product;
        } catch (Exception ex) {
            throw new Exception("Problema al leer un dato ");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public void delete(Pizza pizza) {
        SQLiteDatabase db = databaseHelper.open();
        db.delete(ProductEntity.TABLE_NAME, ProductEntity.ID + " = ?", new String[]{String.valueOf(pizza.getId())});
        db.close();
    }
}
