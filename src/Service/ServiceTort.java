package Service;

import Domain.*;
import Repository.*;

import java.util.Collection;
import java.util.Objects;

public class ServiceTort {
    private IRepository<Tort> repoTort;
    private EntityConverter<Tort> ecTort = new TortConverter();

    SettingsTort setari = SettingsTort.getInstance();

    public ServiceTort() {
        if (Objects.equals(setari.getRepoType(), "memory")) {
            this.repoTort = new Repository<Tort>();
        }
        if (Objects.equals(setari.getRepoType(), "text")) {
            this.repoTort = new TextFileRepository<>(setari.getRepoFile(), ecTort);
        }
        if(Objects.equals(setari.getRepoType(),"binary")){
            this.repoTort = new BinaryFileRepository<Tort>(setari.getRepoFile());
        }
        if(Objects.equals(setari.getRepoType(),"db")){
            this.repoTort = new SQLTortRepository(setari.getRepoFile());
        }
    }

   public void addTort(int id , String tip) throws DuplicateObjectException
   {
       Tort t1= new Tort(id,tip);
       repoTort.addElem(t1);
   }

   public void removeTort(int id) throws RepositoryException
   {
       repoTort.removeElem(id);
   }

   public void updateTort(int id1 , int id2 , String tip1 , String tip2) throws RepositoryException
   {
       Tort t1= new Tort(id1,tip1);
       Tort t2= new Tort(id2,tip2);
       repoTort.updateElem(t1,t2);
   }

   public Collection<Tort> getAllT() throws RepositoryException
   {
       return repoTort.getAll();
   }

   public Tort getByYd(int id) throws RepositoryException
   {
       return repoTort.getById(id);
   }


}
