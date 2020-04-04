package com.atharva.auth.user.dao;

import com.atharva.auth.user.model.UserId;
import com.atharva.auth.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserModel, UserId> {
}
