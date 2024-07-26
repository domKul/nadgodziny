package dominik.nadgodziny.domain.overtime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface OvertimeRepository extends JpaRepository<OvertimeEntity, Long> {

    @Query("select o from OvertimeEntity o where year(o.overtimeDate) = :year and o.status = :status")
    List<OvertimeEntity> findAllByYearAndStatus(int year, String status);

    @Query("select o from OvertimeEntity o where year(o.overtimeDate) = :year and month (o.overtimeDate)= :month")
    List<OvertimeEntity> findAllByYearAndMonth(int year, int month);

}
