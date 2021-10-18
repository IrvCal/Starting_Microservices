package com.irv.restfulservice.user.repository;

import com.irv.restfulservice.user.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository
extends JpaRepository<Post,Integer> {
}
