package Service;

import Domain.Comanda;
import Domain.ComandaConverter;
import Domain.EntityConverter;
import Domain.Tort;
import Repository.IRepository;
import Repository.Repository;
import Repository.RepositoryException;
import Repository.BinaryFileRepository;
import Repository.Settings;
import Repository.TextFileRepository;
import Repository.SQLComandRepo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ServiceComanda {
    //private IRepository<Comanda> repoComanda = new Repository<Comanda>();
    private IRepository<Comanda> repoComanda ;
    private EntityConverter<Comanda> ecComanda = new ComandaConverter();

    Settings setari = Settings.getInstance();

    public ServiceComanda() {
        if (Objects.equals(setari.getRepoType(), "memory")) {
            this.repoComanda = new Repository<Comanda>();
        }
        if (Objects.equals(setari.getRepoType(), "text")) {
            this.repoComanda = new TextFileRepository<>(setari.getRepoFile(), ecComanda);
        }
        if(Objects.equals(setari.getRepoType(),"binary")){
            this.repoComanda = new BinaryFileRepository<Comanda>(setari.getRepoFile());
        }
        if(Objects.equals(setari.getRepoType(),"db")){
            this.repoComanda = new SQLComandRepo(setari.getRepoFile());
        }
    }

    public void addComanda(int id, ArrayList<Tort> lista) throws RepositoryException
    {
        Comanda c1= new Comanda(id,lista);
        repoComanda.addElem(c1);
    }

    public void removeComanda(int id) throws RepositoryException
    {
        repoComanda.removeElem(id);
    }

    public void updateComanda(int id1 , int id2 , ArrayList<Tort> lista) throws RepositoryException
    {
        Comanda c1= new Comanda(id2,lista);
        Comanda c2 = repoComanda.getById(id1);
        repoComanda.updateElem(c2,c1);
    }

    public Collection<Comanda> getAllT() throws RepositoryException
    {
        return repoComanda.getAll();
    }

    public Comanda getById(int id) throws RepositoryException
    {
        return repoComanda.getById(id);
    }


}
