package com.school.security.repositories;

import com.school.security.entities.Stat;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {
    @Query("SELECT MAX(s.date) FROM Stat s WHERE s.date BETWEEN :startDate AND :endDate")
    LocalDate findMaxDateBetweenDates(
            @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(
            value =
                    "SELECT MAX(date_stat) FROM stat WHERE EXTRACT(YEAR FROM date_stat) = :year AND EXTRACT(MONTH FROM date_stat) = :month",
            nativeQuery = true)
    LocalDate findMaxDateInMonthAndYear(@Param("year") int year, @Param("month") int month);

    @Query(
            value =
                    "SELECT MAX(date_stat) FROM stat WHERE EXTRACT(YEAR FROM date_stat) = :year AND EXTRACT(MONTH FROM date_stat) BETWEEN :startMonth AND :endMonth",
            nativeQuery = true)
    LocalDate findMaxDateInTrimester(
            @Param("year") int year,
            @Param("startMonth") int startMonth,
            @Param("endMonth") int endMonth);
}
