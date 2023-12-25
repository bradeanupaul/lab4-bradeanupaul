package Repository;

import Domain.Entity;
import Domain.EntityConverter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileRepository<T extends Entity>extends Repository<T> {
    private String fileName;

    private EntityConverter<T> converter;

    public TextFileRepository(String fileName , EntityConverter<T> converter)
    {
        this.converter = converter;
        this.fileName = fileName;
        try {
            loadFile();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addElem(T o) throws DuplicateObjectException
    {
        super.addElem(o);
        try {
            saveFile();
        }
        catch (IOException e) {
            throw new DuplicateObjectException("Error saving object ",e);
        }
    }

    @Override
    public void removeElem(int id) throws RepositoryException
    {
        super.removeElem(id);
        try {
            saveFile();
        }catch (IOException e){
            throw new RepositoryException("Error saving object " , e);
        }
    }

    @Override
    public void updateElem(T o1 , T o2) throws RepositoryException
    {
        super.updateElem(o1,o2);
        try {
            saveFile();
        }catch (IOException e){
            throw new RepositoryException("Error saving object",e);
        }
    }

    private void saveFile()throws IOException {
        try(FileWriter fw = new FileWriter(fileName)){
            for(T object:elems){
                fw.write(converter.toString(object));
                fw.write("\r\n");
            }
        }
    }

    private void loadFile() throws IOException {
        elems.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line = br.readLine();
            while (line!=null && !line.isEmpty()){
                elems.add(converter.fromString(line));
                line= br.readLine();
            }
        }
    }

}
