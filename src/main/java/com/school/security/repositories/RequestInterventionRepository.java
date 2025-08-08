package com.school.security.repositories;

import com.school.security.entities.InterventionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestInterventionRepository extends JpaRepository<InterventionRequest, Long> {}
