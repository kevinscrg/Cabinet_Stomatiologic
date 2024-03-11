package com.example.lab4.Domain;

import java.io.Serializable;
import java.util.Objects;

public class Pacient extends Entity  implements Serializable {
    private String nume;
    private String prenume;
    private int varsta;

    public Pacient(int id, String nume, String prenume, int v){
        super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = v;
    }

    public int getVarsta() {
        return varsta;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    @Override
    public String toString() {
        return ("\nPacientul cu id-ul " + this.id +": " + this.nume + " " + this.prenume+ " de "+ this.varsta + " ani.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pacient pacient)) return false;
        return getId() == pacient.getId() && getVarsta() == pacient.getVarsta() && Objects.equals(getNume(), pacient.getNume()) && Objects.equals(getPrenume(), pacient.getPrenume());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNume(), getPrenume(), getVarsta());
    }



}
