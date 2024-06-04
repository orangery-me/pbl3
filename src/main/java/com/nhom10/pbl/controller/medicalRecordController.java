package com.nhom10.pbl.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom10.pbl.dto.request.medicalRecordRequest;
import com.nhom10.pbl.dto.respone.medicalRecordResponeModel;
import com.nhom10.pbl.models.ERole;
import com.nhom10.pbl.models.MedicalRecord;
import com.nhom10.pbl.models.UserModel;
import com.nhom10.pbl.security.jwt.JWTService;
import com.nhom10.pbl.security.service.CustomUserDetails;
import com.nhom10.pbl.security.service.CustomUserDetailsService;
import com.nhom10.pbl.services.MedicalRecordServices;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/home")
@AllArgsConstructor
public class medicalRecordController {

    private final MedicalRecordServices medicalRecordServices;
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTService jwtService;

    @GetMapping("/doctor/medical")
    public String getMedicalRecordBydoctorId(Model model, HttpServletRequest request, 
                                                @RequestParam(value = "date", defaultValue = "") String date, 
                                                @RequestParam(value = "namePatient", defaultValue = "") String namePatient,
                                                @RequestParam(value = "today", defaultValue = "") String today){

        String username = jwtService.extractUserNameFromTokenCookie(request);

        if(username != null){
            CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UserModel currUser = userDetails.getUser();

            if(currUser.getRole().getName().equals(ERole.DOCTOR)){
                List<MedicalRecord> afterFilterList = medicalRecordServices.handleFilterMedicalrecord(currUser.getDoctor().getId(), date, namePatient, today);
                List<medicalRecordResponeModel> listMedicalRecordResponeModels = medicalRecordServices.mapdataMedicalRecordResponeModels(afterFilterList);

                model.addAttribute("view", "homePage/homeComponent/medicalRecordInforPage");
                model.addAttribute("file", "medicalRecordInforPage");
        
                model.addAttribute("nav", "homePage/partials/navDoctorLogged");
                model.addAttribute("navState", "navDoctorLogged");
                model.addAttribute("listMedicalRecordRespone", listMedicalRecordResponeModels);
                model.addAttribute("medicalRecordRequest", new medicalRecordRequest());
            }
            model.addAttribute("user", currUser);
        }

        return "homePage/index";
    }

    @GetMapping("/doctor/medical-filter")
    public String getMedicalRecordBydoctorIdAfterFilter(Model model, HttpServletRequest request, 
                                                @RequestParam(value = "date", defaultValue = "") String date, 
                                                @RequestParam(value = "namePatient", defaultValue = "") String namePatient,
                                                @RequestParam(value = "today", defaultValue = "") String today){

        String username = jwtService.extractUserNameFromTokenCookie(request);

        if(username != null){
            CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UserModel currUser = userDetails.getUser();

            if(currUser.getRole().getName().equals(ERole.DOCTOR)){
                List<MedicalRecord> afterFilterList = medicalRecordServices.handleFilterMedicalrecord(currUser.getDoctor().getId(), date, namePatient, today);
                List<medicalRecordResponeModel> listMedicalRecordResponeModels = medicalRecordServices.mapdataMedicalRecordResponeModels(afterFilterList);
           
                model.addAttribute("listMedicalRecordRespone", listMedicalRecordResponeModels);
            }
        }

        return "homePage/homeComponent/medicalRecordInforPageComponent";
    }

    @PutMapping("/doctor/update-medicalrecord")
    @ResponseBody
    public void updateMedicalRecord(medicalRecordRequest medicalRecordRequest){
        medicalRecordServices.updateMedicalRecord(medicalRecordRequest);
    }
}
