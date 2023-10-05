package com.pl.nadgodziny.service;

import com.pl.nadgodziny.model.Overtime;
import com.pl.nadgodziny.repository.OvertimeRepository;
import org.springframework.stereotype.Service;
import sender.SmsSender;

import java.util.List;

@Service
public class OvertimeApiService {

    private final OvertimeRepository overtimeRepository;

    public OvertimeApiService(OvertimeRepository overtimeRepository) {
        this.overtimeRepository = overtimeRepository;
    }

    public List<Overtime>getAllOvertimes(){
        Iterable<Overtime> all = overtimeRepository.findAll();
        return (List<Overtime>) all;
    }

    public void addOvertime(Overtime overtime){
        overtimeRepository.save(overtime);
    }
}
