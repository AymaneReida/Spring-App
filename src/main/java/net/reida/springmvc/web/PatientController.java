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

@Controller
public class PatientController {
    @Autowired
    PatientRepository patientRepository;

    @GetMapping(path = "/patients")
    public String listPatients(Model model,
                               @RequestParam(name = "motCle", defaultValue = "") String mc,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<Patient> patients = patientRepository.findByNomContains(mc, PageRequest.of(page, size));
        model.addAttribute("listPatients", patients.getContent());
        model.addAttribute("pages", new int[patients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("motCle", mc);
        return "patientsView";
    }

    @GetMapping(path = "/deletePatient")
    public String delete(Long id, String motCle, int page, int size) {
        patientRepository.deleteById(id);
        return "redirect:/patients?motCle=" + motCle + "&page=" + page + "&size=" + size;
    }

    @GetMapping(path = "/formPatient")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("mode", "new");
        return "formPatient";
    }

    @PostMapping(path = "/savePatient")
    public String savePatient(Model model, @Valid Patient patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "formPatient";
        }
        patientRepository.save(patient);
        model.addAttribute("patient", patient);
        return "confirmation";
    }

    @GetMapping(path = "/editPatient")
    public String editPatient(Model model, Long id) {
        Patient p = patientRepository.findById(id).get();
        model.addAttribute("patient", p);
        model.addAttribute("mode", "edit");
        return "formPatient";
    }

    /* @GetMapping(path = "/listPatients")
    @ResponseBody
    public List<Patient> list(){
        List<Patient> patients = patientRepository.findAll();
        return patients;
    }
    @GetMapping(path = "/patients/{id}")
    @ResponseBody
    public Patient getOne(@PathVariable Long id){
        return patientRepository.findById(id).get();
    } */
    @GetMapping(path = "/")
    public String index() {
        return "redirect:/patients";
    }
}
