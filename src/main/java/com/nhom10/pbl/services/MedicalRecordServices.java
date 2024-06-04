package com.nhom10.pbl.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nhom10.pbl.dto.request.medicalRecordRequest;
import com.nhom10.pbl.dto.respone.medicalRecordResponeModel;
import com.nhom10.pbl.dto.respone.shiftRespone;
import com.nhom10.pbl.models.MedicalRecord;
import com.nhom10.pbl.models.schedule;
import com.nhom10.pbl.repository.MedicalRecordRepository;
import com.nhom10.pbl.repository.scheduleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MedicalRecordServices {
    private final MedicalRecordRepository medicalRecordRepository;
    
    public void CreateMedicalRecord(schedule schedule){
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setSchedule(schedule);
        
        medicalRecordRepository.save(medicalRecord);
    }

    public List<MedicalRecord>  handleFilterMedicalrecord(Long id, String date, String namePatient, String today){
        List<MedicalRecord> listMedicalRecords = medicalRecordRepository.findAllByDoctorId(id);
        List<MedicalRecord> resultList = new ArrayList<>();

        if(date.equals("") && namePatient.equals("") && today.equals("")){
            return listMedicalRecords;
        }

        if(Boolean.valueOf(today)){
            if(!namePatient.equals("")){
                for (MedicalRecord medicalRecord : listMedicalRecords) {
                    if(medicalRecord.getSchedule().getDate().equals(Date.valueOf(LocalDate.now())) 
                        && medicalRecord.getSchedule().get_patient().getUser().getFullName().toLowerCase().contains(namePatient.toLowerCase())){
                        
                        resultList.add(medicalRecord);
                    }
                }
            }else{
                for (MedicalRecord medicalRecord : listMedicalRecords) {
                    if(medicalRecord.getSchedule().getDate().equals(Date.valueOf(LocalDate.now()))){
                        
                        resultList.add(medicalRecord);
                    }
                }
            }
        }else{
            if(!date.equals("") && !namePatient.equals("")){
                for (MedicalRecord medicalRecord : listMedicalRecords) {
                    if(medicalRecord.getSchedule().getDate().equals(Date.valueOf(date)) 
                        && medicalRecord.getSchedule().get_patient().getUser().getFullName().toLowerCase().contains(namePatient.toLowerCase())){
                        
                        resultList.add(medicalRecord);
                    }
                }
            }else if(date.equals("") && !namePatient.equals("")){
                for (MedicalRecord medicalRecord : listMedicalRecords) {
                    if(medicalRecord.getSchedule().get_patient().getUser().getFullName().toLowerCase().contains(namePatient.toLowerCase())){
                        
                        resultList.add(medicalRecord);
                    }
                }
            }else if(!date.equals("") && namePatient.equals("")){
                for (MedicalRecord medicalRecord : listMedicalRecords) {
                    if(medicalRecord.getSchedule().getDate().equals(Date.valueOf(date))){
                        
                        resultList.add(medicalRecord);
                    }
                }
            }
        }
        return resultList;
    }

    public List<medicalRecordResponeModel> mapdataMedicalRecordResponeModels(List<MedicalRecord> listMedicalRecords){

        List<medicalRecordResponeModel> resultList = new ArrayList<>();

        for (MedicalRecord medicalRecord : listMedicalRecords) {
            medicalRecordResponeModel model = new medicalRecordResponeModel();

            model.setMedicalRecordId(medicalRecord.getId());
            model.setPatientName(medicalRecord.getSchedule().get_patient().getUser().getFullName());
            model.setDate(medicalRecord.getSchedule().getDate());
            
            shiftRespone shift = new shiftRespone();
            shift.setId(medicalRecord.getSchedule().get_shift().getId());
            shift.setTimeStart(medicalRecord.getSchedule().get_shift().getTime_start());
            shift.setTimeEnd(medicalRecord.getSchedule().get_shift().getTime_end());
            model.setShift(shift);

            model.setScheduleId(medicalRecord.getSchedule().getId());
            model.setDiagnosis(medicalRecord.getDiagnosis());

            resultList.add(model);
        }

        return resultList;
    }

    public void updateMedicalRecord(medicalRecordRequest request){

        MedicalRecord medicalRecord = medicalRecordRepository.findById(request.getId()).orElseThrow(()->new RuntimeException("medicalRecord not found"));

        medicalRecord.setDiagnosis(request.getDiagnosis());

        medicalRecordRepository.save(medicalRecord);
    }
}
