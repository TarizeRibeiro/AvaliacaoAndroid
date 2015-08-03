package com.example.fabricio.myapplication.model.persistence;

import com.example.fabricio.myapplication.model.entities.Client;

import java.util.List;

/**
 * Created by Fabricio on 21/07/2015.
 */
public interface ClientRepository {

    public abstract void save(Client client);

    public abstract List<Client> getAll();

    public abstract void delete(Client client);

}

