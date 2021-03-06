package com.test.hospital.orm.repository;

import com.test.hospital.orm.entity.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "procedure", path = "procedure")
public interface ProcedureRepository extends JpaRepository<Procedure, Integer> {
}
