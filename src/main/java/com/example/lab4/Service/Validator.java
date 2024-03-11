package com.example.lab4.Service;

import com.example.lab4.Domain.Entity;
import com.example.lab4.Repository.RepoAbstract;
import com.example.lab4.Repository.RepoException;

public abstract class Validator<T extends Entity> {
    public abstract boolean valid_id(RepoAbstract<T> rep, T ent) throws RepoException;
}
