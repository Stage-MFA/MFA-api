package com.school.security.repositories;

import com.school.security.entities.InterventionRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestInterventionRepository extends JpaRepository<InterventionRequest, Long> {
    List<InterventionRequest> findAllByOrderByStatusDescRequestDateDescPriorityAsc();
}
