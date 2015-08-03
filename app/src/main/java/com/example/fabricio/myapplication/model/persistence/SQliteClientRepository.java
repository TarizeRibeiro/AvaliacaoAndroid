package com.example.fabricio.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fabricio.myapplication.Util.AppUtil;
import com.example.fabricio.myapplication.model.entities.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabricio on 23/07/2015.
 */
public class SQliteClientRepository implements ClientRepository {

    private static SQliteClientRepository singletonInstance;

    private SQliteClientRepository() {
        super();
    }

    public static SQliteClientRepository getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new SQliteClientRepository();
        }
        return singletonInstance;
    }

    @Override
    public void save(Client client) {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = ClientContract.getContentValues(client);

        if (client.getId() == null) {
            db.insert(ClientContract.TABLE, null, values);

        } else {
            String where = ClientContract.ID + " = ?";
            String[] args = {client.getId().toString()};

            db.update(ClientContract.TABLE, values, where, args);
        }

        db.close();
        helper.close();
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();

        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(ClientContract.TABLE, ClientContract.COLUMNS, null, null, null, null, ClientContract.NAME);

        clients = ClientContract.bindList(cursor);

        db.close();
        helper.close();

        return clients;
    }

    @Override
    public void delete(Client client) {

        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();

        String where = ClientContract.ID + " = ?";
        String[] args = {client.getId().toString()};
        db.delete(ClientContract.TABLE, where, args);

        db.close();
        helper.close();

    }
}
