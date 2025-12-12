package com.jaiane.pet_cloud.model;


import jakarta.persistence.*;

import java.util.List;


@Table(name="DB_PET")
@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int idade;
    private String raca;
    private String imagem;
    private List<String> tags;


    public Pet(){

    }


    public Pet(Long id, String name, int idade, String raca,  String imagem, List<String> tags) {
        this.id = id;
        this.name = name;
        this.idade = idade;
        this.raca = raca;
        this.imagem = imagem;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }



    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
