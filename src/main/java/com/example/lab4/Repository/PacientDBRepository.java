package com.example.lab4.Repository;

import com.example.lab4.Domain.Pacient;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class PacientDBRepository extends MemoryRepo<Pacient>{

    Connection connection;

    public PacientDBRepository() {

        Connect();
        CreateTable();
        //initData();

    }


    private void Connect(){
        SQLiteDataSource ds = new SQLiteDataSource();
        String JDBC_URL = "jdbc:sqlite:D:\\User\\UBB\\MAP\\a4-kevinscrg\\lab4\\Cabinet.sqlite";
        ds.setUrl(JDBC_URL);

        try {
            if(connection == null || connection.isClosed())
            {
                connection = ds.getConnection();

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    private void CreateTable(){
        try(final Statement st = connection.createStatement()){
            st.execute("CREATE TABLE IF NOT EXISTS Pacienti(id int primary key, nume navrachar(50), prenume nvarchar(50), varsta int);");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void CloseConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void initData(){
        ArrayList<Pacient> lista = new ArrayList<>();
        Random random = new Random();
        String[] Nume = {"Marian", "Ionescu", "Vintila", "Popa", "Stefanescu"};
        String[] Prenume = {"Eusebiu", "Eustache", "Razvan", "Rares", "Rozeta"};
        for (int i = 0; i < 5; i++) {
            String nume = Nume[random.nextInt(Nume.length)];
            String prenume = Prenume[random.nextInt(Prenume.length)];
            int varsta = random.nextInt(18,89);
            lista.add(new Pacient(i+1,nume,prenume,varsta));
        }
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO Pacienti values (?,?,?,?);")){

            for(Pacient o : lista){
                statement.setInt(1,o.getId());
                statement.setString(2,o.getNume());
                statement.setString(3,o.getPrenume());
                statement.setInt(4,o.getVarsta());
                statement.executeUpdate();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public int getSize() {
        return  getAll().size();
    }

    @Override
    public ArrayList<Pacient> getAll() {

        ArrayList<Pacient> list = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Pacienti")) {
            ResultSet res = statement.executeQuery();
            while(res.next()){
                list.add(new Pacient(res.getInt(1),res.getString(2),res.getString(3),res.getInt(4)));
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return  list;
    }

    @Override
    public Pacient get(int i){
        return  getAll().get(i);
    }

    @Override
    public void add(Pacient o) throws RepoException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO Pacienti values (?,?,?,?);")){

            statement.setInt(1,o.getId());
            statement.setString(2,o.getNume());
            statement.setString(3,o.getPrenume());
            statement.setInt(4,o.getVarsta());
            statement.executeUpdate();

        }catch (SQLException e){
            throw new RepoException(e.getMessage());
        }
    }

    @Override
    public Boolean searchid(int id) {
        ArrayList<Pacient> list = new ArrayList<>();
        boolean rez = false;
        try(PreparedStatement statement = connection.prepareStatement("SELECT * from Pacienti where id = ?")){
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            while(res.next()){
                list.add(new Pacient(res.getInt(1),res.getString(2),res.getString(3),res.getInt(4)));
            }
            if(!list.isEmpty()){rez = true;}
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return rez;
    }

    @Override
    public Pacient getBYid(int id) throws RepoException {

        ArrayList<Pacient> list = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("Select * from Pacienti where id = ?")){
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            while(res.next()){
                list.add(new Pacient(res.getInt(1),res.getString(2),res.getString(3),res.getInt(4)));
            }
        }catch (SQLException e){
            throw new RepoException(e.getMessage());
        }
        if (list.isEmpty()){
            throw new RepoException("Nu exista nici un pacient cu acel id. ");
        }
        return list.getFirst();
    }

    @Override
    public void delete(int ID) throws RepoException {
        try(PreparedStatement state = connection.prepareStatement("DELETE FROM Pacienti WHERE id = ?")){
            state.setInt(1,ID);
            state.executeUpdate();
        }catch (SQLException e){
            throw new RepoException(e.getMessage());
        }

    }


    @Override
    public void update(Pacient ent) throws RepoException {
        try(PreparedStatement statement = connection.prepareStatement("UPDATE Pacienti SET nume = ?, prenume = ?, varsta =? where id = ?")){
            statement.setString(1,ent.getNume());
            statement.setString(2,ent.getPrenume());
            statement.setInt(3,ent.getVarsta());
            statement.setInt(4,ent.getId());
            statement.executeUpdate();
        }catch (SQLException e ){
            throw new RepoException(e.getMessage());
        }
    }
}
