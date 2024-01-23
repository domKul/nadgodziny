package com.pl.nadgodziny.overtime;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
class OvertimeService {
    private final OvertimeRepository overtimeRepository;

     OvertimeService(OvertimeRepository overtimeRepository) {
        this.overtimeRepository = overtimeRepository;
    }


     void addNewOvertime(Overtime overtime) {
         Optional<Overtime> ifNotNull = Optional.ofNullable(overtime);
         try {
             if (ifNotNull.isPresent()){
                 overtimeRepository.save(overtime);
             }
        } catch (Exception e) {
            throw new RuntimeException("Wystąpił błąd podczas zapisu nadgodzin do bazy danych.", e);
        }
    }
    List<Overtime> findOvertimeByMonth(int month){
        return overtimeRepository.findByMonthOvertimeDate(month);
    }

    List<Overtime>getAllOvertimes(){
         return overtimeRepository.findAll();
    }

    int sumDurationByMonth(int month){
         return overtimeRepository.countByDuration(month);
    }

    int sumByGivenStatusAndMonth(int month, String status){
         return overtimeRepository.countByDurationByStatus(month, status);
    }


}
