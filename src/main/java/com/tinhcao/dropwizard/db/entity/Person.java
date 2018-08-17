package com.tinhcao.dropwizard.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "person")
@NamedQueries({
        @NamedQuery(name = Person.FIND_ALL, query = "select p from Person p")
})
public class Person {
    public static final String FIND_ALL = "com.tinhcao.dropwizard.db.entity.Person.findAll";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "name")
    private String name;

    public Person() {
    }

    public Person(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
