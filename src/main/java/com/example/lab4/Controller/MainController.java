package com.example.lab4.Controller;

import com.example.lab4.Domain.Pacient;
import com.example.lab4.Domain.Programare;
import com.example.lab4.Repository.RepoException;
import com.example.lab4.Service.Service;
import com.example.lab4.Service.ServiceProgramare;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainController {

    Service<Pacient> serPAcient;
    ServiceProgramare serProgramare;

    //Tabel Pacienti
    @FXML
    private TableView<Pacient> tabel_Pacient;

    @FXML
    private TableColumn<Pacient, Integer> col_idPacient;

    @FXML
    private TableColumn<Pacient, String> col_Nume;

    @FXML
    private TableColumn<Pacient, String> col_Prenume;

    @FXML
    private TableColumn<Pacient, Integer> col_Varsta;

    final ObservableList<Pacient> Model_Pacient = FXCollections.observableArrayList();

    //Inputuri Pacienti
    @FXML
    private TextField input_idPacient;
    @FXML
    private TextField input_Nume;
    @FXML
    private TextField input_Prenume;
    @FXML
    private TextField input_Varsta;

    //butoane Pacient
    @FXML
    private Button btnAddPacien;
    @FXML
    private Button btnDelPacien;
    @FXML
    private Button btnUpdPacien;
    @FXML
    private  Button btnSelectPacien;
    @FXML
    private  Button btnUnSelectPacien;



    //Tabel Programari
    @FXML
    private TableView<Programare> tabel_Programari;

    @FXML
    private TableColumn<Programare,Integer> col_idProgramare;

    @FXML
    private TableColumn<Programare,String> col_idPacProgr;

    @FXML
    private TableColumn<Programare,String> col_Data;

    @FXML
    private TableColumn<Programare,String> col_Ora;

    @FXML
    private TableColumn<Programare,String> col_Scop;

    final ObservableList<Programare> Model_Programare = FXCollections.observableArrayList();

    // inputuri programari
    @FXML
    private TextField input_idProgramare;
    @FXML
    private TextField input_idPacProg;
    @FXML
    private TextField input_Data;
    @FXML
    private TextField input_Ora;
    @FXML
    private TextField input_Scop;

    // Butoane Programare
    @FXML
    private Button btnAddProgr;
    @FXML
    private Button btnDelProgr;
    @FXML
    private Button btnUpdProgr;
    @FXML
    private Button btnUnSelectProgr;
    @FXML
    private Button btnSelectProgr;

    // Butoane streamuri
    @FXML
    private Button btnProgPerPacient;
    @FXML
    private Button btnProgPerLuna;
    @FXML
    private Button btnZileProgr;
    @FXML
    private Button btnLuniAglo;
    @FXML
    private Button btnDelArea;

    //text area stream
    @FXML
    private TextArea StreamuriText;

    public void setService(Service<Pacient> ser1, ServiceProgramare ser2){
        this.serPAcient = ser1;
        this.serProgramare = ser2;
        initModelPacient();
        initModelProgramare();
        StreamuriText.setEditable(false);
    }


    @FXML
    public void initialize(){
        initializeTabelPacienti();
        initializeTabelProgramari();
    }


    private void initModelPacient(){
        ArrayList<Pacient> lista = serPAcient.getRep().getAll();
     /*   List<Pacient> listaPacient  = StreamSupport.stream(lista.spliterator(), false).collect(Collectors.toList());*/
        Model_Pacient.setAll(lista);
    }



    private void initializeTabelPacienti(){
        col_idPacient.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Nume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        col_Prenume.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        col_Varsta.setCellValueFactory(new PropertyValueFactory<>("varsta"));
        tabel_Pacient.setItems(Model_Pacient);
    }

    private void initModelProgramare(){
        Model_Programare.setAll(serProgramare.getRep().getAll());
        input_idPacProg.setEditable(true);
    }

    private void initializeTabelProgramari(){
        col_idProgramare.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_idPacProgr.setCellValueFactory(progr -> new SimpleStringProperty("" + progr.getValue().getPacient().getId()));
        col_Data.setCellValueFactory(new PropertyValueFactory<>("data"));
        col_Ora.setCellValueFactory(new PropertyValueFactory<>("ora"));
        col_Scop.setCellValueFactory(new PropertyValueFactory<>("scop"));
        tabel_Programari.setItems(Model_Programare);
    }

    // functia principala de clickuri
    @FXML
    private void handleUserPanelClicks(javafx.event.ActionEvent event){
        if(event.getSource() == btnAddPacien){
            GUIAddPacient();
        } else if (event.getSource() == btnDelPacien) {
            GUIStergePacient(getSelectedPacient());
        } else if (event.getSource() == btnUpdPacien) {
            GUIUpdatePacient();
        } else if (event.getSource() == btnSelectPacien) {
            SelectPacient();
        } else if (event.getSource() == btnUnSelectPacien) {
            GolireInputPacient();
        } else if (event.getSource() == btnAddProgr) {
            GUIAddProgramare();
        } else if (event.getSource() == btnDelProgr) {
            GUIStergeProgramare(getSelectedProgramare());
        } else if (event.getSource() == btnUpdProgr) {
            GUIUpdateProgramare();
        } else if (event.getSource() == btnSelectProgr) {
            SelectProgramare();
        } else if (event.getSource() == btnUnSelectProgr) {
            GolireInputProgr();
        } else if (event.getSource() == btnProgPerPacient) {
            GUIProgramariPerPacient();
        } else if (event.getSource() == btnProgPerLuna) {
            GUIProgrPerLuna();
        } else if (event.getSource() == btnZileProgr) {
            GUIZileTrec();
        } else if (event.getSource() == btnLuniAglo) {
            GUILuniAglo();
        } else if (event.getSource() == btnDelArea) {
            GUIGolireArea();
        }
    }

    private void GolireInputPacient(){
        input_Prenume.setText("");
        input_Nume.setText("");
        input_idPacient.setText("");
        input_Varsta.setText("");
    }

    private void GolireInputProgr(){
        input_idProgramare.setText("");
        input_idPacProg.setText("");
        input_Data.setText("");
        input_Ora.setText("");
        input_Scop.setText("");
        input_idPacProg.setEditable(true);
    }

    private Pacient getSelectedPacient(){
        return tabel_Pacient.getSelectionModel().getSelectedItem();
    }
    private Programare getSelectedProgramare(){return tabel_Programari.getSelectionModel().getSelectedItem();}


    private void SelectPacient(){
        if(getSelectedPacient() != null) {
            input_Prenume.setText(getSelectedPacient().getPrenume());
            input_Nume.setText(getSelectedPacient().getNume());
            input_idPacient.setText("" + getSelectedPacient().getId());
            input_Varsta.setText("" + getSelectedPacient().getVarsta());
            initModelPacient();
        }else {
            MessageAlert.showMessage(null,Alert.AlertType.ERROR,"Selectare esuata","Nu ati selectat nici un pacient");
        }
    }

    private void SelectProgramare(){
        if(getSelectedProgramare() != null){
            input_idProgramare.setText(getSelectedProgramare().getId() + "");
            input_idPacProg.setText(getSelectedProgramare().getPacient().getId() + "");
            input_Data.setText(getSelectedProgramare().getData());
            input_Ora.setText(getSelectedProgramare().getOra());
            input_Scop.setText(getSelectedProgramare().getScop());
            initModelProgramare();
            input_idPacProg.setEditable(false);
        }else{
            MessageAlert.showMessage(null,Alert.AlertType.ERROR,"Selectare esuata","Nu ati selectat nicio Programare");
        }
    }

    private void GUIAddPacient(){
        try {
            int id = Integer.parseInt(input_idPacient.getText());
        String nume = input_Nume.getText();
        String prenume = input_Prenume.getText();
        int varsta = Integer.parseInt(input_Varsta.getText());

            serPAcient.add(new Pacient(id,nume,prenume,varsta));
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Adaugare","Pacientul a fost salvat cu succes");
        } catch (RepoException | NumberFormatException e) {
            MessageAlert.showMessage(null,Alert.AlertType.ERROR,"Adaugarea a esuat",e.getMessage());
        }
        GolireInputPacient();

        initModelPacient();
    }

    private void GUIAddProgramare(){
        try {
        int id = Integer.parseInt(input_idProgramare.getText());
        int idPac = Integer.parseInt(input_idPacProg.getText());
        String data = input_Data.getText();
        String ora = input_Ora.getText();
        String scop = input_Scop.getText();

            serProgramare.add(new Programare(id,serPAcient.getRep().getBYid(idPac),scop,data,ora));
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Adaugare","Programarea a fost salvata cu succes");
        } catch (RepoException | NumberFormatException e) {
            MessageAlert.showMessage(null,Alert.AlertType.ERROR,"Adaugarea a esuat",e.getMessage());
        }
        GolireInputProgr();
        initModelProgramare();
    }

    private void GUIStergePacient(Pacient p){
        if(p != null){
            try {
                int k =0;
                for(Programare pro : serProgramare.getRep().getAll()){
                    if(pro.getPacient().getId() == p.getId()){
                        serProgramare.delete(pro.getId());
                        k +=1;
                    }
                }
                serPAcient.delete(p.getId());
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Stergere","Pacientul a fost sters cu succes, au fost sterse "+ k +" programari");

            } catch (RepoException e) {
                MessageAlert.showMessage(null,Alert.AlertType.ERROR,"Stergerea a esuat",e.getMessage());
            }
        }
        else{
            MessageAlert.showMessage(null,Alert.AlertType.ERROR,"Stergerea a esuat","Nu ati selectat pacientul pe care doriti sa-l stergeti.");
        }
        initModelPacient();
        initModelProgramare();
    }

    private void GUIStergeProgramare(Programare p){
        if(p!=null){
            try {
                serProgramare.delete(p.getId());
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Stergere","Programarea a fost stearsa cu succes");
            } catch (RepoException e) {
                MessageAlert.showMessage(null,Alert.AlertType.ERROR,"Stergerea a esuat",e.getMessage());
            }
        }else{
            MessageAlert.showMessage(null,Alert.AlertType.ERROR,"Stergerea a esuat","Nu ati selectat programarea pe care doriti sa o stergeti.");
        }
        initModelProgramare();
    }

    private void GUIUpdatePacient(){
        try{
            int id = Integer.parseInt(input_idPacient.getText());
        String nume = input_Nume.getText();
        String prenume = input_Prenume.getText();
        int varsta = Integer.parseInt(input_Varsta.getText());

            serPAcient.update(new Pacient(id,nume,prenume,varsta));
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Modificare","Pacientul a fost Modificat cu succes");
        } catch (RepoException | NumberFormatException e) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Modificare esuata",e.getMessage());
        }
        GolireInputPacient();

        initModelPacient();
    }

    private void GUIUpdateProgramare(){
        try {
        int id = Integer.parseInt(input_idProgramare.getText());
        int idPac = Integer.parseInt(input_idPacProg.getText());
        String data = input_Data.getText();
        String ora = input_Ora.getText();
        String scop = input_Scop.getText();

            serProgramare.update(new Programare(id,serPAcient.getRep().getBYid(idPac),scop,data,ora));
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Modificare","Programarea a fost Modificata cu succes");
        } catch (RepoException | NumberFormatException e) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Modificare esuata",e.getMessage());
        }
        GolireInputProgr();
        initModelProgramare();
    }

    private void GUIProgramariPerPacient(){
        List<Map.Entry<Pacient, Long>> print = serProgramare.ProgramariPacienti();
        print = print.reversed();
        StringBuilder s = new StringBuilder();
        for (Map.Entry<Pacient, Long> dic : print){
            s.append(dic.getKey()).append(" are ").append(dic.getValue()).append(" programari.");
        }
        StreamuriText.setText(s.toString());
    }

    private void GUIProgrPerLuna(){
        List<Map.Entry<Integer, Long>> print = serProgramare.ProgramariLuna();
        print = print.reversed();
        StringBuilder s = new StringBuilder();
        s.append("\n");
        for (Map.Entry<Integer, Long> dic : print){
            s.append("in luna cu numarul: ").append(dic.getKey()).append(" sunt ").append(dic.getValue()).append(" programari.\n");
        }
        List<Integer> luni = new ArrayList<>();
        for (int i =1; i<=12;i++){luni.add(i);}
        print.forEach(dic -> luni.remove(dic.getKey()));
        for(Integer i : luni){
            s.append("in luna cu numarul: ").append(i).append(" sunt 0 programari.\n");
        }
        StreamuriText.setText(s.toString());
    }

    private void GUIZileTrec(){
        List<String> list = serProgramare.Nrzile();
        StringBuilder s = new StringBuilder();
        for(String p : list){
            s.append(p);
        }
        StreamuriText.setText(s.toString());
    }

    private void GUILuniAglo(){
        List<Map.Entry<Integer, Long>> print = serProgramare.ProgramariLuna();
        print = print.reversed();
        StringBuilder s = new StringBuilder();
        s.append("\n");
        for (Map.Entry<Integer, Long> dic : print){
            s.append("in luna cu numarul: ").append(dic.getKey()).append(" sunt ").append(dic.getValue()).append(" programari.\n");
        }
        StreamuriText.setText(s.toString());
    }

    private void GUIGolireArea(){
        StreamuriText.setText("");
    }




}