package com.pl.nadgodziny.overtime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface OvertimeRepository extends JpaRepository<Overtime, Long> {

    @Query("SELECT o " +
            "FROM Overtime o " +
            "WHERE MONTH(o.overtimeDate) = :month")
    List<Overtime> findByMonthOvertimeDate(int month);

    @Query("SELECT SUM (o.duration) " +
            "FROM Overtime o " +
            "WHERE MONTH(o.overtimeDate) = :month")
    int countByDuration(int month);

    @Query("SELECT SUM (o.duration) " +
            "FROM Overtime o " +
            "WHERE MONTH(o.overtimeDate) = :month " +
            "AND (o.status) = :status")
    int countByDurationByStatus(int month, String status);
}
