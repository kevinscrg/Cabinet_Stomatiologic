package com.example.lab4;

import com.example.lab4.Controller.MainController;
import com.example.lab4.Domain.*;
import com.example.lab4.Repository.*;
import com.example.lab4.Service.*;
import com.example.lab4.UI.Consola;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class MainApplication extends Application {

    private final ValidatorProgramare valPRogramare = new ValidatorProgramare();
    private final ValidatorPacient<Pacient> valPacient = new ValidatorPacient<>();
    private final Properties properties = new Properties();


    @Override
    public void start(Stage stage){

        try{

            InputStream read_prop = new FileInputStream("settings.properties");
            properties.load(read_prop);


            RepoAbstract<Pacient> repPacient;
            if(Objects.equals(properties.getProperty("Repository_Pacient"), "Text")){
                IEntityFactory<Pacient> PacientFactory = new PacientFactory();
                repPacient = new TextFileRepo<>(properties.getProperty("Pacienti"),PacientFactory);
            }
            else if (Objects.equals(properties.getProperty("Repository_Pacient"),"Binary")) {
                repPacient = new BinaryFileRepo<>(properties.getProperty("Pacienti"));

            } else if (Objects.equals(properties.getProperty("Repository_Pacient"), "Memory")) {
                repPacient = new MemoryRepo<>();
            }
            else if(Objects.equals(properties.getProperty("Repository_Pacient"),"DB")){
                repPacient = new PacientDBRepository();
            }else{throw new IOException("setarile nu sunt corecte");}


            RepoAbstract<Programare> repProgramare;
            if(Objects.equals(properties.getProperty("Repository_Progr"), "Text")){
                IEntityFactory<Programare> ProgramareFactory = new ProgramareFactory();
                repProgramare = new TextFileRepo<>(properties.getProperty("Programari"), ProgramareFactory);
            }
            else if (Objects.equals(properties.getProperty("Repository_Progr"),"Binary")) {
                repProgramare = new BinaryFileRepo<>(properties.getProperty("Programari"));

            } else if (Objects.equals(properties.getProperty("Repository_Progr"), "Memory")) {
                repProgramare = new MemoryRepo<>();
            }else if (Objects.equals(properties.getProperty("Repository_Progr"), "DB")) {
                repProgramare = new ProgramareDBRepository();
            }else{
                throw new IOException("setarile nu sunt corecte");
            }

            Service<Pacient> ser1 = new Service<>(repPacient,valPacient);
            ServiceProgramare ser2 = new ServiceProgramare(repProgramare,valPRogramare);


            if(Objects.equals(properties.getProperty("tip"),"consola")){
                Consola cons = new Consola(ser1, ser2);
                cons.meniu();
                javafx. application. Platform. exit();}
            else if (Objects.equals(properties.getProperty("tip"),"app")) {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Cabinet");
                stage.setScene(scene);
                MainController ctrl = fxmlLoader.getController();
                ctrl.setService(ser1,ser2);
                stage.show();
            }
            else{
                throw new IOException("setarile nu sunt corecte");
            }
        } catch (IOException e){
            System.out.println("greseala " + e.getMessage());
            javafx. application. Platform. exit();
        }

    }

    public static void main(String[] args) {
        launch();
    }


}