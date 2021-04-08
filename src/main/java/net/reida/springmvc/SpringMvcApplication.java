package net.reida.springmvc;

import net.reida.springmvc.entities.Patient;
import net.reida.springmvc.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class SpringMvcApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {

        SpringApplication.run(SpringMvcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        patientRepository.save(new Patient(null, "Mohamed", new Date(), 456, false));
        patientRepository.save(new Patient(null, "Aymane", new Date(), 3412, false));
        patientRepository.save(new Patient(null, "Hassan", new Date(), 45, true));

        patientRepository.findAll().forEach(p -> {
            System.out.println(p.toString());
        });
    }
}
