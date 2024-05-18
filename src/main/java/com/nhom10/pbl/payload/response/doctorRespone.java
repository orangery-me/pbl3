package com.nhom10.pbl.payload.response;

import java.util.List;

import com.nhom10.pbl.models.Department;
import lombok.Data;

@Data
public class doctorRespone {
    private Long id;

    private String position;
    private String description;
    private String RoomAddress;
    private String ServicePrices;

    private DepartmentRespone departmentRespone;

    private List<scheduleRespone> listSchedule;

    public void setInitValuedepartmentRespone(Department department) {
        DepartmentRespone departmentRespone = new DepartmentRespone();
        departmentRespone.setId(department.getId());
        departmentRespone.setDescriptionDepartment(department.getDescriptionDepartment());
        departmentRespone.setLocation(department.getLocation());
        departmentRespone.setNameDepartment(department.getNameDepartment());
        departmentRespone.getId();

        this.departmentRespone = departmentRespone;
    }
}
