package Repository;

import Domain.Comanda;
import Domain.Tort;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;

public class SQLComandRepo extends Repository<Comanda>{
    private Connection conn = null;
    private final String dbLocation;

    public SQLComandRepo(String dbLocation) {
        this.dbLocation = "jdbc:sqlite:"+dbLocation;
        openConnection();
        createSchema();
        loadData();
    }

    private void openConnection(){
        try{
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(dbLocation);
            if(conn == null || conn.isClosed())
                conn=ds.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void closeConnection(){
        try{
            if(conn!=null)conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    void createSchema(){
        try {
            try(final Statement stmt = conn.createStatement()){
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS comanda(id int , lista varchar(200));" );
            }
        }catch (SQLException e){
            System.err.println("[ERROR] createSchema : "+ e.getMessage());
        }
    }

    private void loadData(){
        try{
            try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM comanda"); ResultSet rs =statement.executeQuery();) {
                while (rs.next()){
                    String s = rs.getString("lista");
                    ArrayList<Tort> torturi = new ArrayList<>();
                    String[] tokens = s.split(";");
                    for(String t:tokens){
                        String[] tort = t.split(",");
                        Tort trt = new Tort(Integer.parseInt(tort[0]),tort[1]);
                        torturi.add(trt);
                    }
                    Comanda c = new Comanda(rs.getInt("id"),torturi);
                    this.elems.add(c);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addElem(Comanda c) throws DuplicateObjectException{
        super.addElem(c);
        String s="";
        try{
            try(PreparedStatement stmt=conn.prepareStatement("INSERT INTO comanda VALUES (?,?)")){
                stmt.setInt(1,c.getId());
                ArrayList<Tort> torturi = c.getLista();
                for(Tort t:torturi){
                    s+=t.getId()+","+t.getTip()+";";
                }
                stmt.setString(2,s);
                stmt.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeElem(int id) throws RepositoryException{
        super.removeElem(id);
        try {
            try(PreparedStatement stmt=conn.prepareStatement("DELETE FROM comanda WHERE id=?")){
                stmt.setInt(1,id);
                stmt.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateElem(Comanda c1,Comanda c2)throws RepositoryException{
        super.updateElem(c1,c2);
        removeElem(c1.getId());
        addElem(c2);
    }
}
