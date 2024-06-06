package com.nhom10.pbl.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nhom10.pbl.payload.response.PatientResponse;
import com.nhom10.pbl.repository.PatientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientService {
    private PatientRepository patientRepository;

    public List<PatientResponse> getAllPatient() {
        return patientRepository.findAll().stream().map(PatientResponse::mapToResponse).collect(Collectors.toList());
    }
}
