package com.nhom10.pbl.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nhom10.pbl.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    public Optional<UserModel> findByUserName(String userName);

    public Optional<UserModel> getUserById(Long id);

    public void deleteById(@NonNull Long id);

    @NonNull
    public List<UserModel> findAll();

}
