package com.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.client.domain.User;

@Repository
public interface HomeRepository extends JpaRepository<User, String> {


}
