package com.nhom10.pbl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.nhom10.pbl.models.shift;
import com.nhom10.pbl.repository.ShiftRepository;

@Service
public class ShiftServices {
    @Autowired
    private ShiftRepository shiftRepository;

    public List<shift> getShiftList() {
        return shiftRepository.findAll();
    }

    public shift getShiftById(Long id) {
        return shiftRepository.findById(id).get();
    }
}
