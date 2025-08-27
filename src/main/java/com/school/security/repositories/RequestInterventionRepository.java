package com.school.security.repositories;

import com.school.security.entities.InterventionRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestInterventionRepository extends JpaRepository<InterventionRequest, Long> {
    List<InterventionRequest> findAllByOrderByStatusDescRequestDateDescPriorityAsc();

    List<InterventionRequest> findAllByUserUsersIdOrderByStatusDescRequestDateDescPriorityAsc(
            Long idUser);

    List<InterventionRequest> findByRequestDateBetween(LocalDateTime start, LocalDateTime end);

    @Query(
            "SELECT MIN(ir.requestDate), MAX(ir.requestDate) "
                    + "FROM InterventionRequest ir "
                    + "WHERE YEAR(ir.requestDate) = :year")
    List<Object[]> findMinMaxDateByYear(@Param("year") int year);

    @Query(
            "SELECT ir.status, DATE(ir.requestDate), COUNT(ir) "
                    + "FROM InterventionRequest ir "
                    + "WHERE YEAR(ir.requestDate) = :year "
                    + "GROUP BY ir.status, DATE(ir.requestDate) "
                    + "ORDER BY DATE(ir.requestDate)")
    List<Object[]> countByStatusAndDateByYear(@Param("year") int year);

    @Query(
            "SELECT DISTINCT YEAR(ir.requestDate) FROM InterventionRequest ir ORDER BY YEAR(ir.requestDate)")
    List<Integer> findAllYearsWithRequests();
}
