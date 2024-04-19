package com.nhom10.pbl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhom10.pbl.models.Patient;
import com.nhom10.pbl.models.UserModel;
import com.nhom10.pbl.repository.UserRepository;
import com.nhom10.pbl.repository.patientRepository;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private patientRepository patientRepository;

    @Transactional
    public void updatePatientInfo(Long userId, String nhommau, double cannang, double chieucao, String benhnen) {
        // Tìm user với id_user tương ứng
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            // Xử lý khi không tìm thấy user
            throw new RuntimeException("User not found");
        }

        // Lấy thông tin user
        UserModel user = userOptional.get();

        // Tìm patient tương ứng với user
        Patient patient = user.getPatient();
        if (patient == null) {
            // Xử lý khi không tìm thấy patient
            throw new RuntimeException("Patient not found");
        }

        // Cập nhật thông tin bệnh nhân
        patient.setNhommau(nhommau);
        patient.setCannang(cannang);
        patient.setChieucao(chieucao);
        patient.setBenhnen(benhnen);

        // Lưu thay đổi vào cơ sở dữ liệu
        patientRepository.save(patient);
    }

    @Transactional
    public Patient getPatientByUserId(Long userId) {
        return patientRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

}
