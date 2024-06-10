package com.nhom10.pbl.controller.home;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhom10.pbl.models.ERole;
import com.nhom10.pbl.models.Patient;
import com.nhom10.pbl.payload.response.DepartmentRespone;
import com.nhom10.pbl.payload.response.UserResponse;
import com.nhom10.pbl.services.DepartmentServices;
import com.nhom10.pbl.services.PatientService;
import com.nhom10.pbl.security.service.AuthenticateService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {

    private final DepartmentServices departmentServices;
    private final AuthenticateService authenticateService;
    private final PatientService patientServices;

    @GetMapping("/home")
    public String getHomePage(Model model, HttpServletRequest request) {

        UserResponse user = authenticateService.getUserFromCookie(request);

        model.addAttribute("view", "homePage/homeComponent/homePage");
        model.addAttribute("file", "homePage");

        if(user == null){
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        }else{
            if (user.getUsername() != null) {

                if (user.getRole().equals(ERole.DOCTOR.name())) {
                    model.addAttribute("nav", "homePage/partials/navDoctorLogged");
                    model.addAttribute("navState", "navDoctorLogged");
                } else if (user.getRole().equals(ERole.PATIENT.name())) {
                    model.addAttribute("nav", "homePage/partials/navLogged");
                    model.addAttribute("navState", "navLogged");
                    List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
                    model.addAttribute("listDepartmentRespones", listDepartmentRespones);
                }
                model.addAttribute("user", user);
            }
        }

        return "homePage/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "auth/login/login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "auth/register/register";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "homePage/index";
    }

    @RequestMapping("/admin")
    public String adminPage(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/chart";
    }

    @RequestMapping("/admin/accounts")
    public String adminControllUsers(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/accounts";
    }

    @RequestMapping("/admin/articles")
    public String adminControllArticles(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/articles";
    }

    @RequestMapping("/admin/departments")
    public String adminControllDepartments(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/departments";
    }

    @RequestMapping("/admin/doctors")
    public String adminControllDoctors(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/doctors";
    }

    @RequestMapping("/admin/schedules")
    public String adminControllSchedules(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/schedules";
    }

    @GetMapping("/edit")
    public String Update(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);

        if (user.getUsername() != null) {
            model.addAttribute("nav", "homePage/partials/navLogged");
            model.addAttribute("navState", "navLogged");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
            model.addAttribute("user", user);
        }
        return "homePage/homeComponent/edit";
    }

    @GetMapping("/new-content")
    public String newContent(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);

        if (user != null) {
            Patient patient = patientServices.getPatientByUserId(user.getId());
            model.addAttribute("patient", patient);
            model.addAttribute("user", user);

            model.addAttribute("nav", "homePage/partials/navLogged");
            model.addAttribute("navState", "navLogged");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        }
        return "homePage/homeComponent/medicalInfo";
    }

    @GetMapping("/thanhtuu")
    public String thanhtuu() {
        return "homePage/thanhtuu";
    }

    @GetMapping("/huongdan")
    public String huongdan(Model model, HttpServletRequest request){

        UserResponse user = authenticateService.getUserFromCookie(request);

        model.addAttribute("view", "homePage/homeComponent/medical_examination_process_page");
        model.addAttribute("file", "medical_examination_process_page");

        if(user == null){
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        }else{
            if (user.getUsername() != null) {

                if (user.getRole().equals(ERole.DOCTOR.name())) {
                    model.addAttribute("nav", "homePage/partials/navDoctorLogged");
                    model.addAttribute("navState", "navDoctorLogged");
                } else if (user.getRole().equals(ERole.PATIENT.name())) {
                    model.addAttribute("nav", "homePage/partials/navLogged");
                    model.addAttribute("navState", "navLogged");
                    List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
                    model.addAttribute("listDepartmentRespones", listDepartmentRespones);
                }
                model.addAttribute("user", user);
            }
        }

        return "homePage/index";
    }

    @GetMapping("/banggia")
    public String BangGia(Model model, HttpServletRequest request){

        UserResponse user = authenticateService.getUserFromCookie(request);

        model.addAttribute("view", "homePage/homeComponent/priceOfGudmec");
        model.addAttribute("file", "priceOfGudmec");

        if(user == null){
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        }else{
            if (user.getUsername() != null) {

                if (user.getRole().equals(ERole.DOCTOR.name())) {
                    model.addAttribute("nav", "homePage/partials/navDoctorLogged");
                    model.addAttribute("navState", "navDoctorLogged");
                } else if (user.getRole().equals(ERole.PATIENT.name())) {
                    model.addAttribute("nav", "homePage/partials/navLogged");
                    model.addAttribute("navState", "navLogged");
                    List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
                    model.addAttribute("listDepartmentRespones", listDepartmentRespones);
                }
                model.addAttribute("user", user);
            }
        }

        return "homePage/index";
    }

    @GetMapping("/tracuu")
    public String TraCuu(Model model, HttpServletRequest request){

        UserResponse user = authenticateService.getUserFromCookie(request);

        model.addAttribute("view", "homePage/homeComponent/SearchResult");
        model.addAttribute("file", "SearchResult");

        if(user == null){
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        }else{
            if (user.getUsername() != null) {

                if (user.getRole().equals(ERole.DOCTOR.name())) {
                    model.addAttribute("nav", "homePage/partials/navDoctorLogged");
                    model.addAttribute("navState", "navDoctorLogged");
                } else if (user.getRole().equals(ERole.PATIENT.name())) {
                    model.addAttribute("nav", "homePage/partials/navLogged");
                    model.addAttribute("navState", "navLogged");
                    List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
                    model.addAttribute("listDepartmentRespones", listDepartmentRespones);
                }
                model.addAttribute("user", user);
            }
        }

        return "homePage/index";
    }
}
