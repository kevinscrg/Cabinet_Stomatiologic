package com.example.lab4.Service;

import com.example.lab4.Domain.Pacient;
import com.example.lab4.Domain.Programare;
import com.example.lab4.Repository.RepoAbstract;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ServiceProgramare extends Service<Programare>{


    public ServiceProgramare(RepoAbstract<Programare> rep, ValidatorProgramare valid) {
        super(rep, valid);
    }


    public List<Map.Entry<Pacient, Long>> ProgramariPacienti(){
        Map<Pacient, Long> map = rep.getAll().stream().collect(Collectors.groupingBy(Programare::getPacient,Collectors.counting()));
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
    }

    public List<Map.Entry<Integer,Long>> ProgramariLuna(){
        Map<Integer, Long> map = rep.getAll().stream().collect(Collectors.groupingBy(Programare::getLuna,Collectors.counting()));
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
    }

    public List<String> Nrzile(){

        return rep.getAll().stream().sorted(Comparator.comparing(programare -> ChronoUnit.DAYS.between(programare.getLocalDate(), LocalDate.now())))
                .map(programare -> {if(ChronoUnit.DAYS.between(programare.getLocalDate(), LocalDate.now()) > 0) {
                    return String.format("%s Data ultimei Programari: %s, Zile trecute: %s", programare.getPacient(), programare.getData(), ChronoUnit.DAYS.between(programare.getLocalDate(), LocalDate.now()));}
                    return "";
                })
                .collect(Collectors.toList()).reversed();
    }


}
