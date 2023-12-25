package com.example.lab4;

import Domain.Comanda;
import Domain.Tort;
import Repository.DuplicateObjectException;
import Repository.RepositoryException;
import Service.ServiceComanda;
import Service.ServiceTort;
import UI.UI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.action.Action;

import java.util.ArrayList;
import java.util.Collection;

public class HelloController {
    @FXML
    public ListView lstMessage;
    public Button afiseazaComenzi;
    @FXML
    public TextField idLivrat;
    @FXML
    public TextField tipLivrat;
    @FXML
    public TextField stringLivrat;
    @FXML
    public Button adaugaTort;
    public Button adaugaComanda;
    public Button modificaTort;
    public Button modificaComanda;
    public Button stergeTort;
    public Button stergeComanda;
    public TextField idNou;
    @FXML
    ObservableList<String> list = FXCollections.observableList(new ArrayList<>());
    @FXML
    public Button afiseazaTorturi;

    ServiceTort serviceTort= new ServiceTort();
    ServiceComanda serviceComanda = new ServiceComanda();

    @FXML
    public void getAllTorturi(ActionEvent actionEvent) throws RepositoryException {
        list.clear();
        Collection<Tort> lista =serviceTort.getAllT();
        for(Tort t:lista) {
            list.add(t.getId()+" "+t.getTip());
        }
        lstMessage.setItems(list);
    }

    public void getAllComenzi(ActionEvent actionEvent) throws RepositoryException{
        list.clear();
        Collection<Comanda> lista = serviceComanda.getAllT();
        for (Comanda c : lista){
            String s="";
            ArrayList<Tort> torturi = c.getLista();
            for(Tort t:torturi){
                s+=t.getId()+","+t.getTip()+";";
            }
            list.add(c.getId()+" "+s);
        }
        lstMessage.setItems(list);
    }

    @FXML
    public void adaugaTort(ActionEvent actionEvent) {
        int id;
        String t;
        try{
            id = Integer.parseInt(idLivrat.getText());
            t = tipLivrat.getText();
            serviceTort.addTort(id, t);
        }
        catch (NumberFormatException nfe){
            Alert hello = new Alert(Alert.AlertType.ERROR,"Introduceti un numar valid.");
            hello.show();
        }
        catch (DuplicateObjectException e)
        {
            Alert hello = new Alert(Alert.AlertType.ERROR, e.getMessage());
            hello.show();
        }
    }

    @FXML
    public void modificaTort(ActionEvent actionEvent) {
        int idInitial;
        int idSecundar;
    }

    @FXML
    public void stergeTort(ActionEvent actionEvent) {
        int id;
        try{
            id = Integer.parseInt(idLivrat.getText());
            serviceTort.removeTort(id);
        }
        catch (NumberFormatException nfe){
            Alert hello = new Alert(Alert.AlertType.ERROR,"Introduceti un numar valid.");
            hello.show();
        }
        catch (RepositoryException e)
        {
            Alert hello = new Alert(Alert.AlertType.ERROR, e.getMessage());
            hello.show();
        }
    }

    @FXML
    public void stergeComanda(ActionEvent actionEvent) {
        int id;
        try{
            id = Integer.parseInt(idLivrat.getText());
            serviceComanda.removeComanda(id);
        }
        catch (NumberFormatException nfe){
            Alert hello = new Alert(Alert.AlertType.ERROR,"Introduceti un numar valid.");
            hello.show();
        }
        catch (RepositoryException e)
        {
            Alert hello = new Alert(Alert.AlertType.ERROR, e.getMessage());
            hello.show();
        }
    }

    @FXML
    public void adaugaComanda(ActionEvent actionEvent) {
        int id;
        String t;
        ArrayList<Tort> torturi = new ArrayList<>();
        try{
            id = Integer.parseInt(idLivrat.getText());
            t = stringLivrat.getText();
            String[] tokens = t.split(",");
            for (String s:tokens)
            {
                int nr = Integer.parseInt(s);
                torturi.add(serviceTort.getByYd(nr));
            }
            serviceComanda.addComanda(id, torturi);
        }
        catch (NumberFormatException nfe){
            Alert hello = new Alert(Alert.AlertType.ERROR,"Introduceti un numar valid.");
            hello.show();
        }
        catch (RepositoryException e)
        {
            Alert hello = new Alert(Alert.AlertType.ERROR, e.getMessage());
            hello.show();
        }
    }
}