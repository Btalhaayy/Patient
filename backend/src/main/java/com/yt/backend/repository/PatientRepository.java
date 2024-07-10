package com.yt.backend.repository;

import com.yt.backend.model.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    @Query
            ("SELECT s FROM Patient s WHERE s.email = ?1")
    Optional<Patient> findPatientByEmail(String email);
}



