package com.nhom10.pbl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import com.nhom10.pbl.models.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    public @NonNull Optional<Doctor> findById(@NonNull Long id);

    @NonNull
    public List<Doctor> findAll();

    // @Query("SELECT d.id, d.position, d.description, d.RoomAddress,
    // d.ServicePrices, dp.NameDepartment, u.fullName, u.birthday FROM Doctor d JOIN
    // UserModel u ON d.user.id = u.id JOIN Department dp ON d.department.id =
    // dp.id")
    // public List<DoctorInfoResponseImpl> findAllInfo();
}
