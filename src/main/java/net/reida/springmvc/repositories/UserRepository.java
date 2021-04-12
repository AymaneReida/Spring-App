package net.reida.springmvc.repositories;

import net.reida.springmvc.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    // public List<Patient> findByNomContains(String mc);
    public Page<Patient> findByNomContains(String mc, Pageable pageable);
}
