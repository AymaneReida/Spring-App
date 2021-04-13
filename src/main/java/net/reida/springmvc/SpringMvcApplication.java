package net.reida.springmvc;

import net.reida.springmvc.entities.Patient;
import net.reida.springmvc.entities.User;
import net.reida.springmvc.repositories.PatientRepository;
import net.reida.springmvc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class SpringMvcApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

        userRepository.save(new User(null, "user", passwordEncoder.encode("user1234"), 1, "USER", ""));
        userRepository.save(new User(null, "admin", passwordEncoder.encode("admin1234"), 1, "USER,ADMIN", ""));
    }
}
