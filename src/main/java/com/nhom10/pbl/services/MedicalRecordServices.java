package com.nhom10.pbl.services;

import org.springframework.stereotype.Service;

import com.nhom10.pbl.models.MedicalRecord;
import com.nhom10.pbl.models.Schedule;
import com.nhom10.pbl.repository.MedicalRecordRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MedicalRecordServices {
    private final MedicalRecordRepository medicalRecordRepository;

    public void CreateMedicalRecord(Schedule schedule) {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setSchedule(schedule);

        medicalRecordRepository.save(medicalRecord);
    }
}
