package com.test.hospital.config;

import com.test.hospital.orm.entity.Medic;
import com.test.hospital.orm.entity.Procedure;
import com.test.hospital.orm.repository.MedicRepository;
import com.test.hospital.orm.repository.ProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.util.LinkedList;

@Configuration
public class StartupConfig {
    private MedicRepository medicRepository;
    private ProcedureRepository procedureRepository;

    @Autowired
    public StartupConfig(MedicRepository medicRepository,
                         ProcedureRepository procedureRepository) throws ParseException {
        this.medicRepository = medicRepository;
        this.procedureRepository = procedureRepository;
        if (medicRepository.count() == 0) {
            mockMedics();
        }

        if (procedureRepository.count() == 0) {
            mockProcedures();
        }
    }

    private void mockMedics() throws ParseException {
        LinkedList<Medic> medics = new LinkedList<>();

        medics.add(new Medic("John", "Doe", "dentist"));
        medics.add(new Medic("Andrei", "Dragunov", "dentist"));
        medics.add(new Medic("Eva", "Jensen", "dentist"));
        medics.add(new Medic("George", "Vazon", "djentist"));
        medics.add(new Medic("Alexander", "Northern", "dentist"));

        medicRepository.save(medics);
    }

    private void mockProcedures() {
        LinkedList<Procedure> procedures = new LinkedList<>();

        procedures.add(new Procedure("Scaling", ""));
        procedures.add(new Procedure("Teeth whitening", ""));
        procedures.add(new Procedure("Dental prophylaxis", ""));
        procedures.add(new Procedure("Dental bridge", ""));
        procedures.add(new Procedure("Dental implant", ""));

        procedureRepository.save(procedures);
    }
}
