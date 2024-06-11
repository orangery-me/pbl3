package com.nhom10.pbl.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nhom10.pbl.payload.request.PatientUpdateRequest;
import com.nhom10.pbl.services.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PutMapping("/save-or-update/{id}")
    public ResponseEntity<?> saveOrUpdatePatient(@PathVariable("id") Long id,

            @RequestBody PatientUpdateRequest updateRequest) {
        try {
            patientService.updatePatientInfo(id, updateRequest.getNhommau(), updateRequest.getCannang(),
                    updateRequest.getChieucao(), updateRequest.getBenhnen());
            return ResponseEntity.ok().body("{\"message\": \"Patient information updated successfully\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
