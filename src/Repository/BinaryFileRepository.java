package Repository;

import Domain.Entity;

import java.io.*;
import java.util.List;

public class BinaryFileRepository<T extends Entity>extends Repository<T>{
    private String fileName;

    public BinaryFileRepository(String fileName)
    {
        this.fileName = fileName;
    }

    @Override
    public void addElem(T o)throws DuplicateObjectException
    {
        super.addElem(o);
        try {
            saveFile();
        }catch (IOException e) {
            throw new DuplicateObjectException("Error saving file " +e.getMessage());
        }
    }

    @Override
    public void removeElem(int id) throws RepositoryException
    {
        super.removeElem(id);
        try {
            saveFile();
        }catch (IOException e) {
            throw new RepositoryException("Error saving file " + e.getMessage());
        }
    }

    @Override
    public void updateElem(T o1 , T o2) throws RepositoryException
    {
        super.updateElem(o1,o2);
        try {
            saveFile();
        }catch (IOException e){
            throw new RepositoryException("error saving file "+e.getMessage());
        }
    }

    private void loadFile() throws IOException , ClassNotFoundException{
        ObjectInputStream ois = null;
        try
        {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            this.elems = (List<T>) ois.readObject();
        }
        catch (FileNotFoundException e){
            System.out.println("Repo starting a new file");
        }finally {
            if(ois!=null)
                ois.close();
        }
    }

    private void saveFile() throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))){
            oos.writeObject(elems);
        }
    }
}
