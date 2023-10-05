package com.pl.nadgodziny.repository;

import com.pl.nadgodziny.model.Overtime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OvertimeRepository extends CrudRepository<Overtime, Long> {

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
    int coundByDurationBystatus( int month, String status);
}
