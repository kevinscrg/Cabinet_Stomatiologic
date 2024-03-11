package com.example.lab4.Service;

import com.example.lab4.Domain.Entity;
import com.example.lab4.Repository.RepoAbstract;
import com.example.lab4.Repository.RepoException;


public class Service<T extends Entity> {

    protected final RepoAbstract<T> rep;
    protected final Validator<T> valid;

    public Service(RepoAbstract<T> rep, Validator<T> valid) {
        this.rep = rep;
        this.valid = valid;
    }

    public void add(T ent) throws RepoException {
        if (valid.valid_id(rep,ent))rep.add(ent);
        else throw new RepoException("*** exista deja un/o " + ent.getClass().getSimpleName() + " cu acele caracteristice unice ***\n");
    }

    public void update(T ent) throws RepoException {
        if(rep.searchid(ent.getId())) rep.update(ent);
        else throw new RepoException("*** nu exista niciun/nici o " + ent.getClass().getSimpleName() + " cu acel ID! ***\n");
    }

    public void delete(int ID) throws RepoException {
        if(rep.searchid(ID)) rep.delete(ID);
        else throw new RepoException("*** nu exista ce doriti sa stergeti ***\n");
    }
    public RepoAbstract<T> getRep() {
        return rep;
    }
}
