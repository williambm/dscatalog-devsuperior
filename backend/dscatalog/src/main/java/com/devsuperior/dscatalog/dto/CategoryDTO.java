package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.entities.Category;

import java.io.Serializable;

public class CategoryDTO implements Serializable {

    private Long id;
    private String nome;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.nome = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
