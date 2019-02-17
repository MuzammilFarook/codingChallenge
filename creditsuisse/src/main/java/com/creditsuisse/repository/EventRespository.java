package com.creditsuisse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.creditsuisse.models.EventEntity;

public interface EventRespository extends JpaRepository<EventEntity, String> {}