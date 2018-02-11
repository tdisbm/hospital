package com.test.hospital.controller;

import com.test.hospital.orm.entity.Appointment;
import com.test.hospital.orm.entity.Medic;
import com.test.hospital.orm.entity.Procedure;
import com.test.hospital.orm.repository.AppointmentRepository;
import com.test.hospital.service.MedicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import java.util.Date;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    private AppointmentRepository appointmentRepository;
    private EntityManager entityManager;
    private MedicService medicService;

    @Autowired
    public AppointmentController(AppointmentRepository appointmentRepository,
                                 EntityManager entityManager,
                                 MedicService medicService) {
        this.appointmentRepository = appointmentRepository;
        this.entityManager = entityManager;
        this.medicService = medicService;
    }

    @GetMapping("/booking")
    public String createNewForm() {
        return "frontend/appointment/form/booking.html.twig";
    }

    /**
     * 201 - appointment is created
     * 208 - appointment is unavailable
     * 400 - bad request data (medic not identified)
     * TODO: Split this logic
     * @param appointment request body (deserialized json)
     * @return response entity
     */
    @PostMapping(value = "/save",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity create(@RequestBody Appointment appointment) {
        Date appointmentDate = appointment.getDate();
        Medic medic = appointment.getMedic();
        Procedure procedure = appointment.getProcedure();

        if (medic != null) {
            medic = entityManager.find(Medic.class, medic.getId());
        }

        if (procedure != null) {
            procedure = entityManager.find(Procedure.class, procedure.getId());
        }

        if (medic == null || procedure == null || appointment.getId() != null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        appointment.setMedic(medic);
        appointment.setProcedure(procedure);

        try {
            if (medicService.checkAvailability(appointmentDate, medic)) {
                appointmentRepository.save(appointment);
                return new ResponseEntity(HttpStatus.CREATED);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity(HttpStatus.ALREADY_REPORTED);
        }

        return new ResponseEntity(HttpStatus.ALREADY_REPORTED);
    }
}
