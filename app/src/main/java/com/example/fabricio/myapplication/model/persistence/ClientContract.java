package com.example.fabricio.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.fabricio.myapplication.model.entities.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientContract {

    public static final String TABLE = "client";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String PHONE = "phone";

    public static final String ZIP_CODE = "zip_code";
    public static final String TYPE_PATIO = "type_patio";
    public static final String PATIO = "patio";
    public static final String DISTRICT = "district";
    public static final String CITY = "city";
    public static final String COUNTRY = "country";
    public static final String NUMBER = "number";

    public static final String[] COLUMNS = {ID, NAME, AGE, PHONE, ZIP_CODE, TYPE_PATIO, PATIO, DISTRICT, CITY, COUNTRY, NUMBER };


    public static String getCreateSqlTable() {
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(NAME + " TEXT, ");
        sql.append(AGE + " INTEGER, ");
        sql.append(PHONE + " TEXT, ");
        sql.append(ZIP_CODE + " TEXT, ");
        sql.append(TYPE_PATIO + " TEXT, ");
        sql.append(PATIO + " TEXT, ");
        sql.append(DISTRICT + " TEXT, ");
        sql.append(CITY + " TEXT, ");
        sql.append(COUNTRY + " TEXT, ");
        sql.append(NUMBER + " INTEGER ");
        sql.append(" ); ");


        return sql.toString();
    }

    public static ContentValues getContentValues(Client client) {

        ContentValues values = new ContentValues();
        values.put(ClientContract.ID, client.getId());
        values.put(ClientContract.AGE, client.getAge());
        values.put(ClientContract.NAME, client.getName());
        values.put(ClientContract.PHONE, client.getPhone());
        values.put(ClientContract.ZIP_CODE, client.getZipCode());
        values.put(ClientContract.TYPE_PATIO, client.getTypePatio());
        values.put(ClientContract.PATIO, client.getPatio());
        values.put(ClientContract.DISTRICT, client.getDistrict());
        values.put(ClientContract.CITY, client.getCity());
        values.put(ClientContract.COUNTRY, client.getCountry());
        values.put(ClientContract.NUMBER, client.getNumber());


        return values;
    }

    public static Client bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Client client = new Client();
            client.setId(cursor.getInt(cursor.getColumnIndex(ClientContract.ID)));
            client.setName(cursor.getString(cursor.getColumnIndex(ClientContract.NAME)));
            client.setAge(cursor.getInt(cursor.getColumnIndex(ClientContract.AGE)));
            client.setPhone(cursor.getString(cursor.getColumnIndex(ClientContract.PHONE)));
            client.setZipCode(cursor.getString(cursor.getColumnIndex(ClientContract.ZIP_CODE)));
            client.setTypePatio(cursor.getString(cursor.getColumnIndex(ClientContract.TYPE_PATIO)));
            client.setPatio(cursor.getString(cursor.getColumnIndex(ClientContract.PATIO)));
            client.setDistrict(cursor.getString(cursor.getColumnIndex(ClientContract.DISTRICT)));
            client.setCity(cursor.getString(cursor.getColumnIndex(ClientContract.CITY)));
            client.setCountry(cursor.getString(cursor.getColumnIndex(ClientContract.COUNTRY)));
            client.setNumber(cursor.getInt(cursor.getColumnIndex(ClientContract.NUMBER)));

            return client;
        }
        return null;
    }

    public static List<Client> bindList(Cursor cursor) {
        List<Client> clients = new ArrayList<>();

        while (cursor.moveToNext()) {

            clients.add(bind(cursor));
        }

        return clients;
    }

}
