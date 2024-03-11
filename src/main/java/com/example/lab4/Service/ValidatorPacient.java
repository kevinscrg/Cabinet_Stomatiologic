package com.example.lab4.Service;


import com.example.lab4.Domain.Pacient;
import com.example.lab4.Repository.RepoAbstract;

public class ValidatorPacient<T extends Pacient>  extends Validator<T>{

    @Override
    public boolean valid_id(RepoAbstract<T> rep, T ent) {
        for (Pacient o : rep.getAll())if(ent.getId() == o.getId()) return false;
        return true;
    }
}
