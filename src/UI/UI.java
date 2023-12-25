package UI;

import Domain.Comanda;
import Domain.Tort;
import Repository.DuplicateObjectException;
import Repository.RepositoryException;
import Repository.Settings;
import Service.ServiceComanda;
import Service.ServiceTort;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.random.RandomGenerator;

public class UI {
    private ServiceTort serviceT = new ServiceTort();
    private ServiceComanda serviceC =  new ServiceComanda();


    public void addTort()
    {
        try {
            int id;
            String tip;
            System.out.println("Id tort:");
            Scanner cin = new Scanner(System.in);
            id = cin.nextInt();
            System.out.println("Tip tort");
            tip = cin.next();
            serviceT.addTort(id,tip);
            System.out.println("Tortul a fost adaugat");
        }
        catch (DuplicateObjectException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void addComanda()
    {
        try{
            int idC,idT,nr;
            ArrayList<Tort> lista = new ArrayList<Tort>();
            Scanner cin = new Scanner(System.in);
            System.out.println("ID comanda:");
            idC = cin.nextInt();
            System.out.println("Nr de torturi pt aceasta comanda");
            nr = cin.nextInt();
            for(int i=0;i<nr;i++)
            {
                System.out.println("Torturi disponibile:");
                afisareTort();
                System.out.println("ID tort de comandat");
                idT = cin.nextInt();
                Tort t1 = serviceT.getByYd(idT);
                lista.add(t1);
            }
            serviceC.addComanda(idC,lista);
            System.out.println("Comanda a fost adaugata");

        }
        catch (RepositoryException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void removeComanda()
    {
        try
        {
            int id;
            System.out.println("Id comanda:");
            Scanner cin = new Scanner(System.in);
            id = cin.nextInt();
            serviceC.removeComanda(id);
            System.out.println("Comanda a fost stearsa");
        }
        catch (RepositoryException e)
        {
            System.out.println();
        }

    }

    public void updateComanda()
    {
        try
        {
            int id1,id2,nr,idT;
            ArrayList<Tort> lista = new ArrayList<Tort>();
            System.out.println("Id comanda de modificat:");
            Scanner cin = new Scanner(System.in);
            id1 = cin.nextInt();
            System.out.println("ID comanda noua");
            id2 = cin.nextInt();
            System.out.println("Nr de torturi pt aceasta comanda");
            nr = cin.nextInt();
            for(int i=0;i<nr;i++)
            {
                System.out.println("Torturi disponibile:");
                afisareTort();
                System.out.println("ID tort de comandat");
                idT = cin.nextInt();
                Tort t1 = serviceT.getByYd(idT);
                lista.add(t1);
            }
            serviceC.updateComanda(id1 , id2 ,lista);
        }
        catch (RepositoryException e)
        {
            System.out.println(e.getMessage());
        }
    }



    public void addFive()
    {
        try {
            serviceT.addTort(1,"Ciocolata");
            serviceT.addTort(2,"Vanilie");
            serviceT.addTort(3,"Capsuni");
            serviceT.addTort(4,"Zmeura");
            serviceT.addTort(5,"Banane");
            System.out.println("Cele 5 torturi au fost adaugate");
        }
        catch (DuplicateObjectException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void generate100()
    {
        try {
            int i1, i2;
            int id = serviceT.getAllT().size() + 1;
            String[] tipuri = new String[]{"tarta", "tort", "prajitura", "ecler", "placinta"
                    , "strudel", "cozonac", "pandispan", "budinca", "baclava", "croissant", "briosa", "clatita", "cremsnit",
                    "foietaj", "crema", "vafe", "soufle"};
            String[] arome = new String[]{"ciocolata", "vanilie", "zmeura", "cirese", "caramel"
                    , "afine", "capsuni", "visine", "mere", "pere", "caise", "nuca",
                    "rom", "lamaie", "coacaze", "cocos", "migdale", "prune", "struguri", "banane"};
                for (int i = 1; i <= 100; i++) {
                    i1 = ((int) (Math.random() * 10000)) % 18;
                    i2 = ((int) (Math.random() * 10000)) % 20;
                    String tip = tipuri[i1] + "_" + arome[i2];
                    serviceT.addTort(id, tip);
                    id++;
                }
            }
            catch (RepositoryException e) {
                System.out.println(e.getMessage());
        }

    }

    public void generate100C()
    {
        try {
            int id=serviceC.getAllT().size()+1;
            int idTort = serviceT.getAllT().size();
            for(int i=1;i<=100;i++){
                int nrMax = ((int) (Math.random() * 10000)) % 3 +1;
                ArrayList<Tort> lista = new ArrayList<>();
                for(int j=0;j<nrMax;j++){
                    int idT = ((int) (Math.random() * 10000)) % idTort+1;
                    lista.add(serviceT.getByYd(idT));
                }
                serviceC.addComanda(id,lista);
                id++;
            }
        }catch (RepositoryException e){
            System.out.println(e.getMessage());
        }

    }

    public void removeTort()
    {
        try {
            int id;
            System.out.println("Id tort:");
            Scanner cin = new Scanner(System.in);
            id = cin.nextInt();
            serviceT.removeTort(id);
            System.out.println("Tortul a fost sters");
        }
        catch (RepositoryException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void updateTort()
    {
        try
        {
            int id1,id2;
            String tip;
            System.out.println("Id tort de modificat:");
            Scanner cin = new Scanner(System.in);
            id1 = cin.nextInt();
            System.out.println("Id tort nou:");
            id2 = cin.nextInt();
            System.out.println("Tip tort nou:");
            tip = cin.next();
            Tort t1 =serviceT.getByYd(id1);
            serviceT.updateTort(id1 , id2 , t1.getTip() , tip);
        }
        catch (RepositoryException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void afisareTort()
    {
        try {
            for (Tort t : serviceT.getAllT()) {
                System.out.println(t);
            }
        }
        catch (RepositoryException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void afisareComanda()
    {
        try {
            for (Comanda c: serviceC.getAllT())
            {
                System.out.println(c);
            }
        }
        catch (RepositoryException e)
        {
            System.out.println(e.getMessage());
        }
    }



    public void optiuniTort()
    {
        System.out.println("1.Adauga tort");
        System.out.println("2.Sterge tort");
        System.out.println("3.Modifica tort");
        System.out.println("4.Adauga 5 torturi");
        System.out.println("5.Afiseaza torturi");
        System.out.println("6.Adauga comanda");
        System.out.println("7.Sterge comanda");
        System.out.println("8.Modifica comanda");
        System.out.println("9.Afiseaza comenzi");
        System.out.println("10.Generare 100 torturi");
        System.out.println("11.Generare 100 comenzi");
        System.out.println("0.Inchide program");
    }

    public void menu()
    {
        boolean isTrue=true;
        while (isTrue)
        {
            optiuniTort();
            int opt;
            Scanner cin = new Scanner(System.in);
            System.out.println("Optiunea ta:");
            opt = cin.nextInt();
            switch (opt)
            {
                case 1:
                    addTort();
                    break;
                case 2:
                    removeTort();
                    break;
                case 3:
                    updateTort();
                    break;
                case 4:
                    addFive();
                    break;
                case 5:
                    afisareTort();
                    break;
                case 0:
                    isTrue = false;
                    break;
                case 6:
                    addComanda();
                    break;
                case 7:
                    removeComanda();
                    break;
                case 8:
                    updateComanda();
                    break;
                case 10:
                    generate100();
                    break;
                case 11:
                    generate100C();
                    break;
                case 9:
                    afisareComanda();
                    break;

            }
        }
    }

}
