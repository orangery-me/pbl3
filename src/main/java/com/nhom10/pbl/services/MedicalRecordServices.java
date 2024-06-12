package com.nhom10.pbl.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nhom10.pbl.models.MedicalRecord;
import com.nhom10.pbl.models.Schedule;
import com.nhom10.pbl.payload.request.MedicalRecordRequest;
import com.nhom10.pbl.payload.response.MedicalRecordResponeModel;
import com.nhom10.pbl.payload.response.ShiftResponse;
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

    public List<MedicalRecord> handleFilterMedicalrecord(Long id, String date, String namePatient, String today) {
        List<MedicalRecord> listMedicalRecords = medicalRecordRepository.findAllByDoctorId(id);
        List<MedicalRecord> resultList = new ArrayList<>();

        if (date.equals("") && namePatient.equals("") && today.equals("")) {
            return listMedicalRecords;
        }

        if (Boolean.valueOf(today)) {
            if (!namePatient.equals("")) {
                for (MedicalRecord medicalRecord : listMedicalRecords) {
                    if (medicalRecord.getSchedule().getDate().equals(Date.valueOf(LocalDate.now()))
                            && medicalRecord.getSchedule().getPatient().getUser().getFullname().toLowerCase()
                                    .contains(namePatient.toLowerCase())) {

                        resultList.add(medicalRecord);
                    }
                }
            } else {
                for (MedicalRecord medicalRecord : listMedicalRecords) {
                    if (medicalRecord.getSchedule().getDate().equals(Date.valueOf(LocalDate.now()))) {

                        resultList.add(medicalRecord);
                    }
                }
            }
        } else {
            if (!date.equals("") && !namePatient.equals("")) {
                for (MedicalRecord medicalRecord : listMedicalRecords) {
                    if (medicalRecord.getSchedule().getDate().equals(Date.valueOf(date))
                            && medicalRecord.getSchedule().getPatient().getUser().getFullname().toLowerCase()
                                    .contains(namePatient.toLowerCase())) {

                        resultList.add(medicalRecord);
                    }
                }
            } else if (date.equals("") && !namePatient.equals("")) {
                for (MedicalRecord medicalRecord : listMedicalRecords) {
                    if (medicalRecord.getSchedule().getPatient().getUser().getFullname().toLowerCase()
                            .contains(namePatient.toLowerCase())) {

                        resultList.add(medicalRecord);
                    }
                }
            } else if (!date.equals("") && namePatient.equals("")) {
                for (MedicalRecord medicalRecord : listMedicalRecords) {
                    if (medicalRecord.getSchedule().getDate().equals(Date.valueOf(date))) {

                        resultList.add(medicalRecord);
                    }
                }
            }
        }
        return resultList;
    }

    public List<MedicalRecordResponeModel> mapdataMedicalRecordResponeModels(List<MedicalRecord> listMedicalRecords) {

        List<MedicalRecordResponeModel> resultList = new ArrayList<>();

        for (MedicalRecord medicalRecord : listMedicalRecords) {
            MedicalRecordResponeModel model = new MedicalRecordResponeModel();

            model.setMedicalRecordId(medicalRecord.getId());
            model.setPatientName(medicalRecord.getSchedule().getPatient().getUser().getFullname());
            model.setDate(medicalRecord.getSchedule().getDate());

            ShiftResponse shift = new ShiftResponse();
            shift.setId(medicalRecord.getSchedule().getShift().getId());
            shift.setTimeStart(medicalRecord.getSchedule().getShift().getTime_start());
            shift.setTimeEnd(medicalRecord.getSchedule().getShift().getTime_end());
            model.setShift(shift);

            model.setScheduleId(medicalRecord.getSchedule().getId());
            model.setDiagnosis(medicalRecord.getDiagnosis());

            resultList.add(model);
        }

        return resultList;
    }

    public void updateMedicalRecord(MedicalRecordRequest request) {

        MedicalRecord medicalRecord = medicalRecordRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("medicalRecord not found"));

        medicalRecord.setDiagnosis(request.getDiagnosis());

        medicalRecordRepository.save(medicalRecord);
    }
}
