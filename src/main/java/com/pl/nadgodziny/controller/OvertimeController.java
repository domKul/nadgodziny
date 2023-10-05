package com.pl.nadgodziny.controller;

import com.pl.nadgodziny.model.Overtime;
import com.pl.nadgodziny.service.OvertimeApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/nadgodziny")
public class OvertimeController {

    private final OvertimeApiService overtimeApiService;

    public OvertimeController(OvertimeApiService overtimeApiService) {
        this.overtimeApiService = overtimeApiService;
    }


    @GetMapping(value = "all")
    public List<Overtime>getAll(){
        return overtimeApiService.getAllOvertimes();
    }

    @PostMapping(value = "add")
    public void addNewOne(Overtime overtime){
        overtimeApiService.addOvertime(overtime);
    }
}
