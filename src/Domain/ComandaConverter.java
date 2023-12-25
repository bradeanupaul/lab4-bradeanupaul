package Domain;

import java.util.ArrayList;
import java.util.List;

public class ComandaConverter implements EntityConverter<Comanda>{

    @Override
    public String toString(Comanda c)
    {
        String elem = Integer.toString(c.getId());
        for (Tort t:c.getLista())
        {
            elem = elem+ ","+t.getId()+","+t.getTip();
        }
        return elem;
    }

    @Override
    public Comanda fromString(String line){
        String[] tokens = line.split(",");
        int id = Integer.parseInt(tokens[0]);
        ArrayList<Tort> lista_tort = new ArrayList<Tort>();
        int i=1;
        while (i< tokens.length)
        {
            Tort t = new Tort(Integer.parseInt(tokens[i]),tokens[i+1]);
            lista_tort.add(t);
            i=i+2;

        }
        return new Comanda(id, lista_tort);
    }
}
