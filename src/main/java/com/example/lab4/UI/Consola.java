package com.example.lab4.UI;

import com.example.lab4.Domain.Pacient;
import com.example.lab4.Domain.Programare;
import com.example.lab4.Repository.RepoException;
import com.example.lab4.Service.Service;
import com.example.lab4.Service.ServiceProgramare;

import java.util.*;



public class Consola {

    private final Service<Pacient> serPAcient;
    private final ServiceProgramare serProgramare;
    private static final Scanner read = new Scanner(System.in);

    public Consola(Service<Pacient> p1 , ServiceProgramare p2){
        this.serPAcient = p1;
        this.serProgramare = p2;

    }


    public void instante_initiale() {

        try {
            Random random = new Random();
            String[] Nume = {"Marian", "Ionescu", "Vintila", "Popa", "Stefanescu", "Scurtean", "Lunatic", "Strambu","Sanchez","Rus"};
            String[] Prenume = {"Eusebiu", "Eustache", "Razvan", "Rares", "Rozeta","Maria","Luca","Petru","Horatiu","Luna","Petronela"};
            for (int i = 0; i < 100; i++) {
                String nume = Nume[random.nextInt(Nume.length)];
                String prenume = Prenume[random.nextInt(Prenume.length)];
                int varsta = random.nextInt(18,89);
                serPAcient.add(new Pacient(i+1,nume,prenume,varsta));
            }
            String[] Ore = {"16:20","10:11","9:30","10:00","11:15","17:20","9:11","14:30","13:00","16:15","11:20","20:11","18:30","15:00","17:15","13:20","12:11","17:30","15:00","19:15"};
            String[] Scopuri = {"Durere masea", "aparat dentar", "carie", "plomba", "operatie","masea de minte"};
            String[] Dati = {"12/11", "01/02", "20/03", "11/04", "28/07","1/9","2/9","3/7","4/1","5/1","12/10", "01/4", "20/5", "11/12", "28/3","1/4","2/5","3/6","4/7","5/8"};

            int i =0;
           while(i <100) {
               try {
                String scop = Scopuri[random.nextInt(Scopuri.length)];
                String data = Dati[random.nextInt(Dati.length)];
                String ora = Ore[random.nextInt(Ore.length)];

                   serProgramare.add(new Programare(i+1,serPAcient.getRep().get(random.nextInt(100)),scop,data,ora));
                   i+=1;
               } catch (RepoException e) {
                   System.out.println();}
            }
        } catch (RepoException e) {
        System.out.println();
    }

    }



    public void adaugaPacient(){
            try {
                System.out.println("dati id-ul, numele, prenumele si varsta Pacientului.\n");
                int ID = read.nextInt();
                String nume = read.next();
                String prenume = read.next();
                int varsta = read.nextInt();
                Pacient p = new Pacient(ID,nume,prenume,varsta);
                serPAcient.add(p);
            }
            catch(RepoException e){
                System.out.println("\033[31m"+e.getMessage());
            }catch (InputMismatchException e){
                System.out.println("\033[31m"+"eroare, ati  introdus un tip de data gresit.");
                read.next();
            }
    }


    public void updatePacient(){
        try{
            System.out.println("dati id-ul Pacientului pe care doriti sa-l modificati\n");
            int ID = read.nextInt();
            System.out.println("dati noile date ale pacientului (nume prenume varsta)\n");
            String nume = read.next();
            String prenume = read.next();
            int varsta = read.nextInt();
            Pacient p = new Pacient(ID,nume,prenume,varsta);
            serPAcient.update(p);

        } catch(RepoException e){
            System.out.println("\033[31m"+e.getMessage());
        }catch (InputMismatchException e){
            System.out.println("\033[31m"+"eroare, ati  introdus un tip de data gresit.");
            read.next();
        }
    }

    public void deletePacient(){
        try{
            System.out.println("dati id-ul Pacientului pe care doriti sa-l stergeti\n");
            int ID = read.nextInt();
            serPAcient.delete(ID);
        } catch(RepoException e){
            System.out.println("\033[31m"+e.getMessage());
        }catch (InputMismatchException e){
            System.out.println("\033[31m"+"eroare, ati  introdus un tip de data gresit.");
            read.next();
        }
    }


    public void afiseazaPacienti(){
        try {
            if(serPAcient.getRep().getSize() == 0){
                System.out.println("lista de pacienti este goala. ");
            }
            else {
                for (int i = 0; i < serPAcient.getRep().getSize(); i++) {
                    System.out.println(serPAcient.getRep().get(i));
                }
            }
        }
        catch(RepoException e){
            System.out.println("\033[31m"+e.getMessage());
        }
    }

    public void adaugaProgramare(){
        try {
            System.out.println("dati id-ul programarii, id-ul pacientului, data, ora si scopul programarii.\n");
            int IDprogra = read.nextInt();
            int IDpacient = read.nextInt();
            String data = read.next();
            String ora = read.next();
            read.useDelimiter("\\n");
            String scopul = read.next();
            Pacient pacient = serPAcient.getRep().getBYid(IDpacient);
            Programare p = new Programare(IDprogra,pacient,scopul,data,ora);
            serProgramare.add(p);
        }
        catch(RepoException e){
            System.out.println("\033[31m"+e.getMessage());
        }catch (InputMismatchException e){
            System.out.println("\033[31m"+"eroare, ati  introdus un tip de data gresit.");
            read.next();
        }
    }

    public void deleteProgramare(){
        try{
            System.out.println("dati id-ul Programarii pe care doriti sa o stergeti\n");
            int ID = read.nextInt();
            serProgramare.delete(ID);
        } catch(RepoException e){
            System.out.println("\033[31m"+e.getMessage());
        }catch (InputMismatchException e){
            System.out.println("\033[31m"+"eroare, ati  introdus un tip de data gresit.");
            read.next();
        }
    }

    public void updateProgramare(){
        try{
            System.out.println("dati id-ul Programarii pe care doriti sa o modificati\n");
            int ID = read.nextInt();
            System.out.println("dati noile date ale programarii (data, ora, scop)\n");
            String data = read.next();
            String ora = read.next();
            read.useDelimiter("\\n");
            String scopul = read.next();
            Pacient pacient = serProgramare.getRep().getBYid(ID).getPacient();
            Programare p = new Programare(ID,pacient,scopul,data,ora);
            serProgramare.update(p);

        } catch(RepoException e){
            System.out.println("\033[31m"+e.getMessage());
        }catch (InputMismatchException e){
            System.out.println("\033[31m"+"eroare, ati  introdus un tip de data gresit.");
            read.next();
        }
    }

    public void afiseazaProgramari(){
        try {
            if(serProgramare.getRep().getSize() ==0){
                System.out.println("lista de programari este goala. ");
            }
            else {
                for (int i = 0; i < serProgramare.getRep().getSize(); i++) {
                    System.out.println(serProgramare.getRep().get(i));
                }
            }
        }
        catch(RepoException e){
            System.out.println("\033[31m"+e.getMessage());
        }
    }

    public void ProgramariPerPacient(){
        List<Map.Entry<Pacient, Long>> print = serProgramare.ProgramariPacienti();
        print.reversed().forEach(dic -> System.out.println(dic.getKey() + " are "+ dic.getValue() +" programari."));
    }
    public void ProgramariPerLuna(){
        List<Map.Entry<Integer, Long>> print = serProgramare.ProgramariLuna();
        print.reversed().forEach(dic -> System.out.println("in luna cu numarul: "+dic.getKey() + " sunt "+ dic.getValue() +" programari."));

        List<Integer> luni = new ArrayList<>();
        for (int i =1; i<=12;i++){luni.add(i);}
        print.forEach(dic -> luni.remove(dic.getKey()));
        luni.forEach(i -> System.out.println("in luna cu numarul: " + i + " sunt 0 programari."));
    }

    public void LuniAglomerate(){
        List<Map.Entry<Integer, Long>> print = serProgramare.ProgramariLuna();
        print.reversed().forEach(dic -> System.out.println("in luna cu numarul: "+dic.getKey() + " sunt "+ dic.getValue() +" programari."));
    }

    public void Nrzile(){
        List<String> list = serProgramare.Nrzile();
        list.forEach(System.out::println);
    }


    public void  printMeniu(){
        System.out.println("""
                \u001B[0m
                ----------------------------
                Ce doriti sa faceti""");
        System.out.println("1. Meniu de Pacenti.");
        System.out.println("2. Meniu de Programari.");
        System.out.println("3. Numarul de programari per pacient.");
        System.out.println("4. Programarile per luna.");
        System.out.println("6. luniile cele mai aglomerate.");
        System.out.println("x. iesire din aplicatie.");
        System.out.println("----------------------------");

    }

    public void printMeniuPacient(){
        System.out.println("\u001B[0m"+"\n----------------------------");
        System.out.println("1. adauga un pacient.");
        System.out.println("2. modificarea unui pacient.");
        System.out.println("3. stergerea unui pacient.");
        System.out.println("a. afiseaza lista de pacienti.");
        System.out.println("x. iesire din meniul de pacienti.");
        System.out.println("----------------------------");
    }


    public void meniuPacient(){
        String b;
        labelPacient:
        while(true){
            printMeniuPacient();
            System.out.println("dati optiunea: ");
            b = read.next();
            switch (b){
                case "1":
                    adaugaPacient();
                    break;
                case "2":
                    updatePacient();
                    break;
                case "3":
                    deletePacient();
                    break;
                case "a":
                    afiseazaPacienti();
                    break;
                case "x":
                    break labelPacient;
                case null:
                default:
                    System.out.println("optiune gresita, reincercati. ");
                    break;
            }
        }
    }

    public void printMeniuProgramare(){
        System.out.println("\u001B[0m"+"\n----------------------------");
        System.out.println("1. adauga o programare.");
        System.out.println("2. modificarea unei programari.");
        System.out.println("3. stergerea unei programari.");
        System.out.println("a. afiseaza lista de programari.");
        System.out.println("x. iesire din meniul de programari.");
        System.out.println("----------------------------");
    }


    public void meniuProgramare(){
        String b;
        labelProgramare:
        while(true){
            read.useDelimiter("[\\n ]");
            printMeniuProgramare();
            System.out.println("dati optiunea: ");
            b = read.next();
            switch (b){
                case "1":
                    adaugaProgramare();
                    break;
                case "2":
                    updateProgramare();
                    break;
                case "3":
                    deleteProgramare();
                    break;
                case "a":
                    afiseazaProgramari();
                    break;
                case "x":
                    break labelProgramare;
                case null:
                default:
                    System.out.println("optiune gresita, reincercati. ");
                    break;
            }
        }
    }

    public void meniu(){
        String a;
        instante_initiale();
        label:
        while (true){
            printMeniu();
            System.out.println("dati optiunea: ");
            a = read.next();
            switch (a) {
                case "1":
                    meniuPacient();
                    break;
                case "2":
                    meniuProgramare();
                    break;
                case "3":
                    ProgramariPerPacient();
                    break ;
                case "4":
                    ProgramariPerLuna();
                    break;
                case"5":
                    Nrzile();
                    break;
                case "6":
                    LuniAglomerate();
                    break;
                case "x":
                    break label;
                case null:
                default:
                    System.out.println("optiune gresita, reincercati. ");
                    break;
            }

        }

    }




}
