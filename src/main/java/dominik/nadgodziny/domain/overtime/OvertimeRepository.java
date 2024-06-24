package dominik.nadgodziny.domain.overtime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OvertimeRepository extends JpaRepository<OvertimeEntity, Long>{


}
