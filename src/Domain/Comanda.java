package Domain;

import java.util.ArrayList;

public class Comanda extends Entity{

    private ArrayList<Tort> lista;

    public Comanda(int id , ArrayList<Tort> lista)
    {
        super(id);
        this.lista = lista;
    }

    public ArrayList<Tort> getLista(){return this.lista;}

    public void setLista(ArrayList<Tort> lista) {this.lista = lista;}

    public String toString(){
        return "Comanda:{id = "+getId() + ", lista = "+lista+"}";
    }


}
