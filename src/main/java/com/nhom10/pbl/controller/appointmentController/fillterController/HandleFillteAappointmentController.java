package com.nhom10.pbl.controller.appointmentController.fillterController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

import com.nhom10.pbl.models.UserModel;
import com.nhom10.pbl.payload.response.BookingModel;
import com.nhom10.pbl.payload.response.DepartmentRespone;
import com.nhom10.pbl.payload.response.DoctorRespone;
import com.nhom10.pbl.payload.response.ScheduleRespone;
import com.nhom10.pbl.payload.resquest.ScheduleRequest;
import com.nhom10.pbl.security.jwt.JWTService;
import com.nhom10.pbl.security.service.CustomUserDetails;
import com.nhom10.pbl.security.service.CustomUserDetailsService;
import com.nhom10.pbl.services.DepartmentServices;
import com.nhom10.pbl.services.DoctorServices;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("home/user/appointment")
public class HandleFillteAappointmentController {
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTService jwtService;
    private final DepartmentServices _departmentServices;
    private final DoctorServices _doctorServices;

    @GetMapping("/{departmentId}")
    @ResponseBody
    public DepartmentRespone getDepartment(@PathVariable String departmentId) {

        return _departmentServices.getDepartmentByID(Long.parseLong(departmentId));
    }

    @GetMapping("/doctors/{departmentId}")
    public String getListDoctorOfDepartment(@PathVariable("departmentId") String departmentId,
            HttpServletRequest request, Model model) {

        String username = jwtService.extractUserNameFromTokenCookie(request);

        List<DoctorRespone> ListdoctorResponses = _departmentServices.getListDoctor(Long.parseLong(departmentId),
                false);
        List<DepartmentRespone> listDepartmentRespones = _departmentServices.getAllDepartmentRespones();

        model.addAttribute("view", "homePage/homeComponent/fillterDoctorPage");
        model.addAttribute("file", "fillterDoctorPage");
        model.addAttribute("nav", "homePage/partials/navLogged");
        model.addAttribute("navState", "navLogged");
        model.addAttribute("listDoctorOfDepartment", ListdoctorResponses);
        model.addAttribute("listDepartmentRespones", listDepartmentRespones);

        if (username != null) {
            CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UserModel currUser = userDetails.getUser();
            model.addAttribute("user", currUser);
        }

        return "homePage/index";
    }

    @GetMapping("/doctor/{doctorId}")
    public String getDetailDoctor(@PathVariable("doctorId") String doctorId, HttpServletRequest request, Model model) {

        String username = jwtService.extractUserNameFromTokenCookie(request);

        List<BookingModel> ListBookingAvailable = _doctorServices
                .getListBookingModelsOfDoctor(Long.parseLong(doctorId));
        DoctorRespone doctorRespone = _doctorServices.getDoctorResponeById(Long.parseLong(doctorId));
        List<DepartmentRespone> listDepartmentRespones = _departmentServices.getAllDepartmentRespones();

        model.addAttribute("view", "homePage/homeComponent/bookAppointment");
        model.addAttribute("file", "bookAppointment");
        model.addAttribute("nav", "homePage/partials/navLogged");
        model.addAttribute("navState", "navLogged");
        model.addAttribute("doctor", doctorRespone);
        model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        model.addAttribute("ListbookingAvailable", ListBookingAvailable);
        model.addAttribute("scheduleRequest", new ScheduleRequest());

        if (username != null) {
            CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UserModel currUser = userDetails.getUser();
            model.addAttribute("user", currUser);
        }

        return "homePage/index";
    }

    @GetMapping("/doctors/today/{departmentId}")
    public String getListDoctorOfDepartmentToday(@PathVariable("departmentId") String departmentId,
            @RequestParam(value = "q", defaultValue = "") String doctorName,
            @RequestParam(value = "gender", defaultValue = "") Boolean gender,
            @RequestParam(value = "shift", defaultValue = "") String shift, Model model) {

        List<DoctorRespone> listDoctorResponeToday = _departmentServices.listDoctorToday(Long.parseLong(departmentId));
        List<DoctorRespone> searchByName = _departmentServices.searchByNameDoctor(listDoctorResponeToday, doctorName);
        List<DoctorRespone> finaListDoctor = _departmentServices.filterDoctorByGenderAndShift(searchByName, gender,
                shift, "today");
        model.addAttribute("listDoctorOfDepartment", finaListDoctor);

        return "homePage/homeComponent/listDoctor/listDoctorComponent";
    }

    @GetMapping("/doctors/tomorrow/{departmentId}")
    public String getListDoctorOfDepartmentTomorrow(@PathVariable("departmentId") String departmentId,
            @RequestParam(value = "q", defaultValue = "") String doctorName,
            @RequestParam(value = "gender", defaultValue = "") Boolean gender,
            @RequestParam(value = "shift", defaultValue = "") String shift, Model model) {

        List<DoctorRespone> listDoctorResponeTomorrow = _departmentServices
                .listDoctorTomorrow(Long.parseLong(departmentId));
        List<DoctorRespone> searchByName = _departmentServices.searchByNameDoctor(listDoctorResponeTomorrow,
                doctorName);
        System.out.println(searchByName + "========================");
        List<DoctorRespone> finaListDoctor = _departmentServices.filterDoctorByGenderAndShift(searchByName, gender,
                shift, "tomorrow");
        model.addAttribute("listDoctorOfDepartment", finaListDoctor);

        return "homePage/homeComponent/listDoctor/listDoctorComponent";
    }

    @GetMapping("/doctors/nextsevenday/{departmentId}")
    public String getListDoctorOfDepartmentNextSevenDay(@PathVariable("departmentId") String departmentId,
            @RequestParam(value = "q", defaultValue = "") String doctorName,
            @RequestParam(value = "gender", defaultValue = "") Boolean gender,
            @RequestParam(value = "shift", defaultValue = "") String shift, Model model) {

        List<DoctorRespone> listDoctorResponeNextSevenDay = _departmentServices
                .listDoctorNextSevenDay(Long.parseLong(departmentId));
        List<DoctorRespone> searchByName = _departmentServices.searchByNameDoctor(listDoctorResponeNextSevenDay,
                doctorName);
        List<DoctorRespone> finaListDoctor = _departmentServices.filterDoctorByGenderAndShift(searchByName, gender,
                shift, "nextseven");
        model.addAttribute("listDoctorOfDepartment", finaListDoctor);

        return "homePage/homeComponent/listDoctor/listDoctorComponent";
    }

    @GetMapping("/doctors/byName/{departmentId}")
    public String getListDoctorOfDepartmentByName(@PathVariable("departmentId") String departmentId,
            @RequestParam(value = "q", defaultValue = "") String doctorName,
            @RequestParam(value = "gender", defaultValue = "") Boolean gender,
            @RequestParam(value = "shift", defaultValue = "") String shift, Model model) {

        List<DoctorRespone> listDoctorRespone = _departmentServices.getListDoctor(Long.parseLong(departmentId), true);
        List<DoctorRespone> searchByName = _departmentServices.searchByNameDoctor(listDoctorRespone, doctorName);
        List<DoctorRespone> finaListDoctor = _departmentServices.filterDoctorByGenderAndShift(searchByName, gender,
                shift, "none");
        model.addAttribute("listDoctorOfDepartment", finaListDoctor);

        return "homePage/homeComponent/listDoctor/listDoctorComponent";
    }

    @GetMapping("/doctor/schedules/{doctorId}")
    @ResponseBody
    public List<ScheduleRespone> getListScheduleOfDoctor(@PathVariable("doctorId") String doctorId) {
        return _doctorServices.getListScheduleResponsesOfDoctor(Long.parseLong(doctorId));
    }

}
