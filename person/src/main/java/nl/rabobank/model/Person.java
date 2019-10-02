package nl.rabobank.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String name;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Address address;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
