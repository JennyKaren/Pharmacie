package com.medical.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import com.medical.model.Patient;
import com.medical.repository.PatientRepository;

@Service
public class PatientDetailsService implements UserDetailsService {
    
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Patient patient = patientRepository.findByUsername(username);
        if (patient == null) {
            throw new UsernameNotFoundException("Patient not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
            patient.getUsername(), 
            patient.getPassword(),
            true, true, true, true,
            java.util.Collections.singletonList(new SimpleGrantedAuthority("ROLE_PATIENT"))
        );
    }
}
