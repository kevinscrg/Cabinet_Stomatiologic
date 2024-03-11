package com.example.lab4.Service;

import com.example.lab4.Domain.Programare;
import com.example.lab4.Repository.RepoAbstract;
import com.example.lab4.Repository.RepoException;

public class ValidatorProgramare extends Validator<Programare>{
    @Override
    public boolean valid_id(RepoAbstract<Programare> rep, Programare ent) throws RepoException {
        try {
            for (Programare o : rep.getAll()) if (ent.getId() == o.getId()) return false;

            int zi_ent = Integer.parseInt(ent.getData().split("/")[0]);
            int luna_ent = Integer.parseInt(ent.getData().split("/")[1]);

            int ora_ent = Integer.parseInt(ent.getOra().split(":")[0]);
            int minute_ent = Integer.parseInt(ent.getOra().split(":")[1]);

            for (Programare o : rep.getAll()) {
                int zi_o = Integer.parseInt(o.getData().split("/")[0]);
                int luna_o = Integer.parseInt(o.getData().split("/")[1]);
                if (luna_o == luna_ent) if (zi_o == zi_ent) {
                    int ora_o = Integer.parseInt(o.getOra().split(":")[0]);
                    int minute_o = Integer.parseInt(o.getOra().split(":")[1]);
                    if (ora_o == ora_ent) if (minute_ent == minute_o) return false;
                }
            }
            return true;}   catch(NumberFormatException | ArrayIndexOutOfBoundsException e)  {throw new RepoException("*** nu ati introdus datele corect. ***");}
    }
}
