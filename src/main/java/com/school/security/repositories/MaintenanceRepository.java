package com.school.security.repositories;

import com.school.security.entities.Maintenance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    @Query(
            "SELECT MIN(ir.startDate), MAX(ir.startDate) "
                    + "FROM Maintenance ir "
                    + "WHERE YEAR(ir.startDate) = :year")
    List<Object[]> findMinMaxDateByYear(@Param("year") int year);

    @Query(
            "SELECT ir.status, DATE(ir.startDate), COUNT(ir) "
                    + "FROM Maintenance ir "
                    + "WHERE YEAR(ir.startDate) = :year "
                    + "GROUP BY ir.status, DATE(ir.startDate) "
                    + "ORDER BY DATE(ir.startDate)")
    List<Object[]> countByStatusAndDateByYear(@Param("year") int year);

    @Query("SELECT DISTINCT YEAR(ir.startDate) FROM Maintenance ir ORDER BY YEAR(ir.startDate)")
    List<Integer> findAllYearsWithRequests();
}
