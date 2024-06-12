package com.nhom10.pbl.payload.request;

import lombok.Data;

@Data
public class MedicalRecordRequest {
    private Long id;
    private String diagnosis;
}
