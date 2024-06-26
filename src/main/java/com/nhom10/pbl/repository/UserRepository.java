package com.nhom10.pbl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;

import com.nhom10.pbl.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    public @NonNull Optional<UserModel> findById(@NonNull Long id);

    @EntityGraph(attributePaths = { "role" })
    public Optional<UserModel> findByUserName(String userName);

    public Optional<List<UserModel>> findByUserNameContaining(String userName);

    public Optional<List<UserModel>> findByEmailContaining(String email);

    public Optional<List<UserModel>> findByTelephoneContaining(String phone);

    public Optional<List<UserModel>> findByFullnameContaining(String fullName);

    public void deleteById(@NonNull Long id);

    @NonNull
    public List<UserModel> findAll();

}
