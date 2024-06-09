package com.nhom10.pbl.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nhom10.pbl.models.Patient;
import com.nhom10.pbl.models.UserModel;
import com.nhom10.pbl.payload.response.PatientResponse;
import com.nhom10.pbl.repository.PatientRepository;
import com.nhom10.pbl.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientService {
    private UserRepository userRepository;
    private PatientRepository patientRepository;

    public void createNewPatient(UserModel user) {
        Patient patient = Patient.builder().user(user).build();
        patientRepository.save(patient);
    }

    public List<PatientResponse> getAllPatient() {
        return patientRepository.findAll().stream().map(PatientResponse::mapToResponse).collect(Collectors.toList());
    }

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
