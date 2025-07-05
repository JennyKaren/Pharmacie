package com.medical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.medical.model.Patient;
import com.medical.repository.PatientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showPatientLogin() {
        return "redirect:/login"; // Redirection vers la page de login existante
    }

    @GetMapping("/register")
    public String showPatientRegister(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient-register";
    }

    @PostMapping("/register")
    public String registerPatient(@ModelAttribute Patient patient, Model model) {
        try {
            // Vérifier si le username existe déjà
            if (patientRepository.findByUsername(patient.getUsername()) != null) {
                model.addAttribute("error", "Username already exists");
                return "patient-register";
            }
            
            // Encoder le mot de passe avant de sauvegarder
            patient.setPassword(passwordEncoder.encode(patient.getPassword()));
            
            // Sauvegarder le patient
            patientRepository.save(patient);
            
            // Ajouter un message de succès
            model.addAttribute("success", "Registration successful! Please login.");
            
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred during registration: " + e.getMessage());
            return "patient-register";
        }
    }
}
