package com.irv.restfulservice.user.repository;

import com.irv.restfulservice.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends JpaRepository<User, Integer> {


}
