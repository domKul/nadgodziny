package dominik.nadgodziny.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OvertimeRepository extends JpaRepository<Overtime, Long>{

}
