package Repository;
import Domain.Tort;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;

public class SQLTortRepository extends Repository<Tort> {

    private Connection conn = null;
    private final String dbLocation;

    public SQLTortRepository(String dbLocation) {
        this.dbLocation = "jdbc:sqlite:" + dbLocation;
        openConnection();
        createSchema();
        loadData();
    }

    private void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(dbLocation);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadData(){
        try{
            try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM tort");ResultSet rs =statement.executeQuery();) {
                while (rs.next()){
                    Tort t = new Tort(rs.getInt("id"),rs.getString("tip"));
                    this.elems.add(t);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    void createSchema() {
        try {
            try (final Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS tort(id int ,tip varchar(50));");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }
    public void addElem(Tort t) throws DuplicateObjectException
    {
        super.addElem(t);
        try{
            try(PreparedStatement stmt=conn.prepareStatement("INSERT INTO tort VALUES (?,?)")){
                stmt.setInt(1,t.getId());
                stmt.setString(2,t.getTip());
                stmt.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        }
    @Override
    public void removeElem(int id) throws RepositoryException
    {
        super.removeElem(id);
        try {
            try(PreparedStatement stmt=conn.prepareStatement("DELETE FROM tort WHERE id=?")){
                stmt.setInt(1,id);
                stmt.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public ArrayList<Tort> getAll() throws DuplicateObjectException
    {
        ArrayList<Tort> torturi = new ArrayList<>();
        try {
            try(PreparedStatement stmt=conn.prepareStatement("SELECT * FROM tort");ResultSet rs =stmt.executeQuery();) {
                while (rs.next()){
                    Tort t = new Tort(rs.getInt("id"),rs.getString("tip"));
                    torturi.add(t);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return torturi;
    }
    @Override
    public void updateElem(Tort t1 , Tort t2) throws RepositoryException
    {
        super.updateElem(t1,t2);
        removeElem(t1.getId());
        addElem(t2);
    }
}

