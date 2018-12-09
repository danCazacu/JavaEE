package entity;

import org.eclipse.persistence.annotations.CascadeOnDelete;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "S")
@ManagedBean(name = "studentEntity")
@SessionScoped
@CascadeOnDelete
public class StudentEntity extends PersonEntity {

    @Column
    private Integer year = 1;

    public StudentEntity(){}

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }


}
