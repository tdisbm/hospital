package com.test.hospital.orm.repository;

import com.test.hospital.orm.entity.Medic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "medic", path = "medic")
public interface MedicRepository extends JpaRepository<Medic, Integer> {
}
