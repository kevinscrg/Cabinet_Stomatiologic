package com.example.lab4.Repository;

import com.example.lab4.Domain.Pacient;
import com.example.lab4.Domain.Programare;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class ProgramareDBRepository extends MemoryRepo<Programare>{

    Connection connection;


    public ProgramareDBRepository(){

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

    @Override
    public void CloseConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void CreateTable(){
        try(final Statement st = connection.createStatement()){
            st.execute("CREATE TABLE IF NOT EXISTS Programari(id int primary key, id_pacient int , scop nvarchar(50), data_luna nvarchar(50), ora nvarchar(50), FOREIGN KEY(id_pacient) REFERENCES Pacienti(id) ON DELETE CASCADE );");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void initData(){
        ArrayList<Programare> lista = new ArrayList<>();
        Random random = new Random();

        String[] Ore = {"16:20","10:11","9:30","10:00","11:15"};
        String[] Scopuri = {"Durere masea", "aparat dentar", "carie", "plomba", "operatie"};
        String[] Dati = {"12/11", "01/02", "20/03", "11/04", "28/07"};
        for (int i = 0; i < 5; i++) {
            String scop = Scopuri[random.nextInt(Scopuri.length)];
            String data = Dati[random.nextInt(Dati.length)];
            String ora = Ore[random.nextInt(Ore.length)];
            lista.add(new Programare(i+1,getPacienti().get(random.nextInt(5)),scop,data,ora));
        }
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO Programari values (?,?,?,?,?);")){

            for(Programare o : lista){
                seturiProgramare(statement, o);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    //se foloseste strict in initData pt generarea pseudo aleatorie
    private ArrayList<Pacient> getPacienti()
    {
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

    private void seturiProgramare(PreparedStatement statement, Programare o) throws SQLException {
        statement.setInt(1,o.getId());
        statement.setInt(2,o.getPacient().getId());
        statement.setString(3,o.getScop());
        statement.setString(4,o.getData());
        statement.setString(5,o.getOra());
        statement.executeUpdate();
    }



    @Override
    public ArrayList<Programare> getAll() {
        ArrayList<Programare> list = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Programari P JOIN Pacienti X on P.id_pacient = X.id;")){
            ResultSet res = statement.executeQuery();
            while(res.next()){
                int id = res.getInt(1);
                Geturi(list, res, id);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    private void Geturi(ArrayList<Programare> list, ResultSet res, int id) throws SQLException {
        int id_pacient = res.getInt(2);
        String scop = res.getString(3);
        String data = res.getString(4);
        String ora = res.getString(5);
        String nume = res.getString(7);
        String prenume = res.getString(8);
        int varsta = res.getInt(9);
        list.add(new Programare(id,new Pacient(id_pacient,nume,prenume,varsta),scop,data,ora));
    }

    @Override
    public int getSize() {
        return  getAll().size();
    }

    @Override
    public Programare get(int i){
        return  getAll().get(i);
    }

    @Override
    public Boolean searchid(int id) {
        ArrayList<Programare> list = new ArrayList<>();
        boolean rez = false;
        try(PreparedStatement statement = connection.prepareStatement("SELECT * from Programari P JOIN Pacienti X on X.id = P.id_pacient where P.id = ? ;")){
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Geturi(list, res, id);
            }
            if(!list.isEmpty()){rez = true;}
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return rez;
    }

    @Override
    public void add(Programare p) {
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO Programari values(?,?,?,?,?)")){

            seturiProgramare(statement, p);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int ID) throws RepoException {
        try(PreparedStatement state = connection.prepareStatement("DELETE FROM Programari WHERE id = ?")){
            state.setInt(1,ID);
            state.executeUpdate();
        }catch (SQLException e){
            throw new RepoException(e.getMessage());
        }
    }

    @Override
    public void update(Programare ent) throws RepoException {
        try(PreparedStatement statement = connection.prepareStatement("UPDATE Programari SET  id_pacient = ?, scop =?, data_luna = ?, ora = ? where id = ?")){
            statement.setInt(1,ent.getPacient().getId());
            statement.setString(2,ent.getScop());
            statement.setString(3,ent.getData());
            statement.setString(4,ent.getOra());
            statement.setInt(5,ent.getId());
            statement.executeUpdate();
        }catch (SQLException e ){
            throw new RepoException(e.getMessage());
        }
    }

    @Override
    public Programare getBYid(int id) throws RepoException {
        ArrayList<Programare> list = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("Select * from Programari P JOIN Pacienti X on X.id = P.id_pacient where P.id = ?")){
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Geturi(list, res, id);
            }
        }catch (SQLException e){
            throw new RepoException(e.getMessage());
        }
        if (list.isEmpty()){
            throw new RepoException("Nu exista nici o Programare cu acel id. ");
        }
        return list.getFirst();
    }
}
