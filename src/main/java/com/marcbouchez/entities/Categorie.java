package com.marcbouchez.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Categorie {

    private int id;
    private String name;
    private List<Jeu> jeux;

    public Categorie() {
    }

    public Categorie(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "categories")
    public List<Jeu> getJeux() {
        return jeux;
    }

    public void setJeux(List<Jeu> jeux) {
        this.jeux = jeux;
    }

    @Override
    public String toString() {
        return name;
    }
}
