package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@NamedQuery(name = "findByName",
        query = "SELECT p FROM PersonEntity p WHERE p.name = :name")
@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type")
public class PersonEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String email;

    public PersonEntity(){
    }

    public PersonEntity( String name, String email) {
        this.name = name;
        this.email = email;
    }

    public PersonEntity(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
