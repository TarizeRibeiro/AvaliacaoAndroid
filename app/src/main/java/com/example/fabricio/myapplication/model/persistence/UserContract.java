package com.example.fabricio.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.fabricio.myapplication.model.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserContract {

    public static final String TABLE = "user";
    public static final String ID = "id";
    public static final String USERNAME = "userName";
    public static final String PASSWORD = "password";

    public static final String[] COLUMNS = {ID, USERNAME, PASSWORD };

    public static String getUserDefault(User user) {
        StringBuilder sql = new StringBuilder();

        sql.append(" INSERT INTO ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(USERNAME);
        sql.append(" , " + PASSWORD + " ");

        sql.append(" ) ");
        sql.append(" VALUES ( ");
        sql.append(" '" + user.getUserName() + "', ");
        sql.append(" '" + user.getPassword() + "' ");
        sql.append(" ); ");


        return sql.toString();
    }

    public static String getCreateSqlTable() {
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(USERNAME + " TEXT, ");
        sql.append(PASSWORD + " TEXT ");
        sql.append(" ); ");

        return sql.toString();
    }

    public static ContentValues getContentValues(User user) {

        ContentValues values = new ContentValues();
        values.put(UserContract.ID, user.getId());
        values.put(UserContract.USERNAME, user.getUserName());
        values.put(UserContract.PASSWORD, user.getPassword());

        return values;
    }

    public static String[] getArgsSearch(User user) {

        List<String> values = new ArrayList<>();
        if (user.getId() != null) {
            values.add(user.getId().toString());
        }
        if (user.getUserName() != null) {
            values.add(user.getUserName());
        }
        if (user.getPassword() != null) {
            values.add(user.getPassword());
        }
        return values.size() == 0 ? null : values.toArray(new String[0]);
    }

    public static String buildWereSearch(User user) {
        StringBuilder where = new StringBuilder();

        if (user.getId() != null) {
            where.append(UserContract.ID + " = ? ");
        }
        if (user.getUserName() != null) {
            where.append(where.length() > 0 ? " and " : "");
            where.append(UserContract.USERNAME + " = ? ");
        }
        if (user.getPassword() != null) {
            where.append(where.length() > 0 ? " and " : "");
            where.append(UserContract.PASSWORD + " = ? ");
        }


        return where.toString();
    }

    public static User bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(UserContract.ID)));
            user.setUserName(cursor.getString(cursor.getColumnIndex(UserContract.USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(UserContract.PASSWORD)));

            return user;
        }
        return null;
    }

    public static List<User> bindList(Cursor cursor) {
        List<User> users = new ArrayList<>();

        while (cursor.moveToNext()) {

            users.add(bind(cursor));
        }

        return users;
    }

}
