package com.example.lab4.Domain;

public interface IEntityFactory<T extends Entity> {
    T creareEntitate(String linie);
    String scriereEntitate(T ent);
}
