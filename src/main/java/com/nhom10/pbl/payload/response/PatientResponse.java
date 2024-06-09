package com.nhom10.pbl.payload.response;

import com.nhom10.pbl.models.Patient;
import com.nhom10.pbl.models.UserModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PatientResponse {
    private Long id;
    private UserModel user;
    private String nhomMau;
    private double canNang;
    private double chieuCao;
    private String benhNen;

    public static PatientResponse mapToResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .user(patient.getUser())
                .nhomMau(patient.getNhommau())
                .canNang(patient.getCannang())
                .chieuCao(patient.getChieucao())
                .benhNen(patient.getBenhnen())
                .build();
    }
}
