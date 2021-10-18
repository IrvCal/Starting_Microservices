package com.irv.restfulservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)// no se jalaran los datos del user a menos que se haga un select *
    @JsonIgnore //hay que agregar esta etiqueta porque si no entra en un ciclo infinito (quitar para ver que pas)
    private User user;

    public User getUser() {
        return user;
    }


    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
