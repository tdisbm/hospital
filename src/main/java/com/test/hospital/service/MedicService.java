package com.test.hospital.service;

import com.test.hospital.orm.entity.Medic;
import com.test.hospital.orm.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MedicService {
    /**
     * Duration of one session is set to 60 min but will be
     * better to store this value in procedure table (in this case should
     * be implemented a complex availability check)
     */
    private final Integer MEDIC_SESSION_MINUTES = 60;
    private final Integer ONE_MINUTE_MILLIS = 60000;

    private AppointmentRepository appointmentRepository;

    @Autowired
    public MedicService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Boolean checkAvailability(Date appointmentDate, Medic medic) {
        Date criteriaStartDate = new Date(appointmentDate.getTime() -
                (MEDIC_SESSION_MINUTES * ONE_MINUTE_MILLIS));
        Date criteriaEndDate = new Date(appointmentDate.getTime() +
                (MEDIC_SESSION_MINUTES * ONE_MINUTE_MILLIS));

        return appointmentRepository.hasAppointments(
                medic, criteriaStartDate, criteriaEndDate) == 0;
    }
}
