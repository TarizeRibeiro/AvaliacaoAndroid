package com.example.fabricio.myapplication.model.persistence;

import com.example.fabricio.myapplication.model.entities.User;

import java.util.List;

/**
 * Created by Tarize on 01/08/2015.
 */
public interface UserRepository {

    public abstract void save(User user);

    public abstract List<User> search(User user);
}

