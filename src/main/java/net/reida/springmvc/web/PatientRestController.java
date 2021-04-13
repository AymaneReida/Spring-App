package net.reida.springmvc.web;

import net.reida.springmvc.entities.Patient;
import net.reida.springmvc.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PatientRestController {
    @Autowired
    PatientRepository patientRepository;

    @GetMapping(path = "/listPatients")
    public List<Patient> list(){
        List<Patient> patients = patientRepository.findAll();
        return patients;
    }
    @GetMapping(path = "/patients/{id}")
    public Patient getOne(@PathVariable Long id){

        return patientRepository.findById(id).get();
    }
}
