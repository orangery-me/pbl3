package com.nhom10.pbl.payload.response;

import com.nhom10.pbl.models.Department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRespone {

    private Long id;
    private String NameDepartment;
    private String DescriptionDepartment;
    private String Location;

    public static DepartmentRespone mapToDepartmentRespone(Department department) {
        return new DepartmentRespone(department.getId(),
                department.getNameDepartment(),
                department.getDescriptionDepartment(),
                department.getLocation());
    }
}
