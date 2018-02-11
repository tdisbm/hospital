package com.test.hospital.orm.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"date", "medic_id", "procedure_id"})
})
@DynamicUpdate
@Data
public class Appointment {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy HH:mm", timezone = "Europe/Chisinau")
    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(nullable = false)
    public String fullName;

    @Column(nullable = false)
    public String email;

    @Column
    public String phoneNumber;

    @Column
    public String comment;

    @RestResource(exported = false)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Medic medic;

    @RestResource(exported = false)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Procedure procedure;
}
