package com.test.hospital.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.hospital.orm.entity.Medic;
import com.test.hospital.orm.repository.MedicRepository;
import com.test.hospital.service.MedicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/medic")
public class MedicController {
    private MedicRepository medicRepository;
    private MedicService medicService;

    @Autowired
    public MedicController(MedicRepository medicRepository, MedicService medicService) {
        this.medicRepository = medicRepository;
        this.medicService = medicService;
    }

    @PostMapping("/availability/check/{id}")
    public ResponseEntity availabilityCheck(
            @PathVariable("id") Integer id,

            @RequestParam
            @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy HH:mm")
                    Date date) {
        Medic medic = medicRepository.findOne(id);
        return medicService.checkAvailability(date, medic)
            ? new ResponseEntity(HttpStatus.OK)
            : new ResponseEntity(HttpStatus.ALREADY_REPORTED);
    }
}
