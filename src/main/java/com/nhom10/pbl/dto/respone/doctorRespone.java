package com.nhom10.pbl.dto.respone;
import java.util.List;

import com.nhom10.pbl.models.department;

import lombok.Data;

@Data
public class doctorRespone {
    private Long id;

    private String position;
    private String description;
    private String RoomAddress;
    private String ServicePrices;

    private departmentRespone departmentRespone;
    
    private List<scheduleRespone> listSchedule;

    public void setInitValuedepartmentRespone(department department){
        departmentRespone departmentRespone = new departmentRespone();
        departmentRespone.setId(department.getId());
        departmentRespone.setDescriptionDepartment(department.getDescriptionDepartment());
        departmentRespone.setLocation(department.getLocation());
        departmentRespone.setNameDepartment(department.getNameDepartment());
        departmentRespone.getId();

        this.departmentRespone = departmentRespone;
    }
}
