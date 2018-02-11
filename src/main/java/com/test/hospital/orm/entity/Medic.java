package com.test.hospital.orm.entity;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table
@DynamicUpdate
@Data
public class Medic {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String degree;

    public Medic() {}

    public Medic(String firstName, String lastName, String degree) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.degree = degree;
    }
}
