package com.nhom10.pbl.payload.request;

import com.nhom10.pbl.models.Department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class DepartmentRequest {
    private String nameDepartment;
    private String descriptionDepartment;
    private String location;

    public static DepartmentRequest mapToDepartmentRequest(Department department) {
        return new DepartmentRequest(department.getNameDepartment(),
                department.getDescriptionDepartment(),
                department.getLocation());
    }
}
