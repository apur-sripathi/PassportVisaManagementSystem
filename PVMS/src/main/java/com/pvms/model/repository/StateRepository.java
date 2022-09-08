package com.pvms.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pvms.model.pojo.State;

public interface StateRepository extends JpaRepository<State, String> {
}
