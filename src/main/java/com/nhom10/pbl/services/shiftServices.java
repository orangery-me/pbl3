package com.nhom10.pbl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.nhom10.pbl.models.Shift;
import com.nhom10.pbl.repository.ShiftRepository;

@Service
public class ShiftServices {
    @Autowired
    private ShiftRepository shiftRepository;

    public List<Shift> getShiftList() {
        return shiftRepository.findAll();
    }

    public Shift getShiftById(Long id) {
        return shiftRepository.findById(id).get();
    }
}
