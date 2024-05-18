package com.nhom10.pbl.controller.appointmentController.fillterController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

import com.nhom10.pbl.dto.request.scheduleRequest;
import com.nhom10.pbl.dto.respone.bookingModel;
import com.nhom10.pbl.dto.respone.departmentRespone;
import com.nhom10.pbl.dto.respone.doctorRespone;
import com.nhom10.pbl.dto.respone.scheduleRespone;
import com.nhom10.pbl.models.UserModel;
import com.nhom10.pbl.security.jwt.JWTService;
import com.nhom10.pbl.security.service.CustomUserDetails;
import com.nhom10.pbl.security.service.CustomUserDetailsService;
import com.nhom10.pbl.services.departmentServices;
import com.nhom10.pbl.services.doctorServices;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Controller
@RequestMapping("home/user/appointment")
public class HandleFillteAappointmentController {
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTService jwtService;
    private final departmentServices _departmentServices;
    private final doctorServices _doctorServices;

    @GetMapping("/{departmentId}")
    @ResponseBody
    public departmentRespone getDepartment(@PathVariable String departmentId){
        
        return _departmentServices.getDepartmentByID(Long.parseLong(departmentId));
    }

    @GetMapping("/doctors/{departmentId}")
    public String getListDoctorOfDepartment(@PathVariable("departmentId") String departmentId, HttpServletRequest request, Model model) {

        String username = jwtService.extractUserNameFromTokenCookie(request);
        
        List<doctorRespone> ListdoctorResponses = _departmentServices.getListDoctor(Long.parseLong(departmentId), false);
        List<departmentRespone> listDepartmentRespones =  _departmentServices.getAllDepartmentRespones();
        
        model.addAttribute("view", "homePage/homeComponent/fillterDoctorPage");
            model.addAttribute("file", "fillterDoctorPage");
            model.addAttribute("nav", "homePage/partials/navLogged");
            model.addAttribute("navState", "navLogged");
            model.addAttribute("listDoctorOfDepartment", ListdoctorResponses);
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);

        if(username != null){
            CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UserModel currUser = userDetails.getUser();
            model.addAttribute("user", currUser);
        }

        return "homePage/index";
    }


    @GetMapping("/doctor/{doctorId}")
    public String getDetailDoctor(@PathVariable("doctorId") String doctorId, HttpServletRequest request, Model model) {

        String username = jwtService.extractUserNameFromTokenCookie(request);

        List<bookingModel> ListBookingAvailable = _doctorServices.getListBookingModelsOfDoctor(Long.parseLong(doctorId));
        doctorRespone doctorRespone = _doctorServices.getDoctorResponeById(Long.parseLong(doctorId));
        List<departmentRespone> listDepartmentRespones =  _departmentServices.getAllDepartmentRespones();
        
            model.addAttribute("view", "homePage/homeComponent/bookAppointment");
            model.addAttribute("file", "bookAppointment");
            model.addAttribute("nav", "homePage/partials/navLogged");
            model.addAttribute("navState", "navLogged");
            model.addAttribute("doctor", doctorRespone);
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
            model.addAttribute("ListbookingAvailable", ListBookingAvailable);
            model.addAttribute("scheduleRequest", new scheduleRequest());

            if (username != null) {
                CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                UserModel currUser = userDetails.getUser();
                model.addAttribute("user", currUser);
            }    

        return "homePage/index";
    }

    @GetMapping("/doctors/today/{departmentId}")
    public String getListDoctorOfDepartmentToday(@PathVariable("departmentId") String departmentId,@RequestParam(value = "q", defaultValue = "") String doctorName, Model model) {

        List<doctorRespone> listDoctorResponeToday = _departmentServices.listDoctorToday(Long.parseLong(departmentId));
        List<doctorRespone> finaListDoctor = _departmentServices.searchByNameDoctor(listDoctorResponeToday, doctorName);
        model.addAttribute("listDoctorOfDepartment", finaListDoctor);

        return "homePage/homeComponent/listDoctor/listDoctorComponent";
    }

    @GetMapping("/doctors/tomorrow/{departmentId}")
    public String getListDoctorOfDepartmentTomorrow(@PathVariable("departmentId") String departmentId, @RequestParam(value = "q", defaultValue = "") String doctorName, Model model) {

        List<doctorRespone> listDoctorResponeTomorrow = _departmentServices.listDoctorTomorrow(Long.parseLong(departmentId));
        List<doctorRespone> finaListDoctor = _departmentServices.searchByNameDoctor(listDoctorResponeTomorrow, doctorName);
        model.addAttribute("listDoctorOfDepartment", finaListDoctor);

        return "homePage/homeComponent/listDoctor/listDoctorComponent";
    }

    @GetMapping("/doctors/nextsevenday/{departmentId}")
    public String getListDoctorOfDepartmentNextSevenDay(@PathVariable("departmentId") String departmentId, @RequestParam(value = "q", defaultValue = "") String doctorName, Model model) {

        List<doctorRespone> listDoctorResponeNextSevenDay = _departmentServices.listDoctorNextSevenDay(Long.parseLong(departmentId));
        List<doctorRespone> finaListDoctor = _departmentServices.searchByNameDoctor(listDoctorResponeNextSevenDay, doctorName);
        model.addAttribute("listDoctorOfDepartment", finaListDoctor);

        return "homePage/homeComponent/listDoctor/listDoctorComponent";
    }

    @GetMapping("/doctors/byName/{departmentId}")
    public String getListDoctorOfDepartmentByName(@PathVariable("departmentId") String departmentId, @RequestParam(value = "q", defaultValue = "") String doctorName, Model model) {

        List<doctorRespone> listDoctorRespone = _departmentServices.getListDoctor(Long.parseLong(departmentId), false);
        List<doctorRespone> finaListDoctor = _departmentServices.searchByNameDoctor(listDoctorRespone, doctorName);
        model.addAttribute("listDoctorOfDepartment", finaListDoctor);

        return "homePage/homeComponent/listDoctor/listDoctorComponent";
    }

    @GetMapping("/doctor/schedules/{doctorId}")
    @ResponseBody
    public List<scheduleRespone> getListScheduleOfDoctor(@PathVariable("doctorId") String doctorId) {
        return _doctorServices.getListScheduleResponsesOfDoctor(Long.parseLong(doctorId));
    }
    
}
