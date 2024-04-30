package com.nhom10.pbl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.nhom10.pbl.models.shift;
import com.nhom10.pbl.repository.shiftRepository;

@Service
public class shiftServices {
    @Autowired
    private shiftRepository shiftRepository;

    public List<shift> getShiftList() {
        return shiftRepository.findAll();
    }
}
