package com.school.security.repositories;

import com.school.security.entities.Intervention;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {

    @Query(
            "SELECT MIN(ir.dateIntervention), MAX(ir.dateIntervention) "
                    + "FROM Intervention ir "
                    + "WHERE YEAR(ir.dateIntervention) = :year")
    List<Object[]> findMinMaxDateByYear(@Param("year") int year);

    @Query(
            "SELECT ir.status, DATE(ir.dateIntervention), COUNT(ir) "
                    + "FROM Intervention ir "
                    + "WHERE YEAR(ir.dateIntervention) = :year "
                    + "GROUP BY ir.status, DATE(ir.dateIntervention) "
                    + "ORDER BY DATE(ir.dateIntervention)")
    List<Object[]> countByStatusAndDateByYear(@Param("year") int year);

    @Query(
            "SELECT DISTINCT YEAR(ir.dateIntervention) FROM Intervention ir ORDER BY YEAR(ir.dateIntervention)")
    List<Integer> findAllYearsWithRequests();
}
