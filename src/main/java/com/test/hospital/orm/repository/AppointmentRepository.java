package com.test.hospital.orm.repository;

import com.test.hospital.orm.entity.Appointment;
import com.test.hospital.orm.entity.Medic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;

@RepositoryRestResource(collectionResourceRel = "appointment", path = "appointment")
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query("select count(a) from Appointment a where (a.date between :date_start and :date_end) and a.medic = :medic")
    Integer hasAppointments(
            @Param("medic") Medic medic,
            @Param("date_start") Date start,
            @Param("date_end") Date end);
}
