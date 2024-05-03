package com.nhom10.pbl.controller.appointmentController.fillterController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

import com.nhom10.pbl.dto.request.scheduleRequest;
import com.nhom10.pbl.dto.respone.bookingModel;
import com.nhom10.pbl.dto.respone.departmentRespone;
import com.nhom10.pbl.dto.respone.doctorRespone;
import com.nhom10.pbl.dto.respone.scheduleRespone;
import com.nhom10.pbl.services.departmentServices;
import com.nhom10.pbl.services.doctorServices;



@Controller
@RequestMapping("home/user/appointment")
public class HandleFillteAappointmentController {

    private final departmentServices _departmentServices;
    private final doctorServices _doctorServices;

    public HandleFillteAappointmentController(
        departmentServices departmentServices, 
        doctorServices doctorServices) 
    {
        this._departmentServices = departmentServices;
        this._doctorServices = doctorServices;
    }

    @GetMapping("/{departmentId}")
    @ResponseBody
    public departmentRespone getDepartment(@PathVariable String departmentId){
        
        return _departmentServices.getDepartmentByID(Long.parseLong(departmentId));
    }

    @GetMapping("/doctors/{departmentId}")
    public String getListDoctorOfDepartment(@PathVariable String departmentId, Model model) {
        List<doctorRespone> ListdoctorResponses = _departmentServices.getListDoctor(Long.parseLong(departmentId), false);
        List<departmentRespone> listDepartmentRespones =  _departmentServices.getAllDepartmentRespones();
        
        model.addAttribute("view", "homePage/homeComponent/fillterDoctorPage");
            model.addAttribute("file", "fillterDoctorPage");
            model.addAttribute("nav", "homePage/partials/navLogged");
            model.addAttribute("navState", "navLogged");
            model.addAttribute("listDoctorOfDepartment", ListdoctorResponses);
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);

        return "homePage/index";
    }


    @GetMapping("/doctor/{doctorId}")
    public String getDetailDoctor(@PathVariable String doctorId, Model model) {

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

        return "homePage/index";
    }

    // @GetMapping("/doctor/{doctorId}")
    // @ResponseBody
    // public List<bookingModel> getDetailDoctor(@PathVariable String doctorId, Model model) {
    //     doctorRespone doctorRespone = _doctorServices.getDoctorResponeById(Long.parseLong(doctorId));
    //     List<departmentRespone> listDepartmentRespones =  _departmentServices.getAllDepartmentRespones();

    //     List<bookingModel> result = _doctorServices.getListBookingModelsOfDoctor(Long.parseLong(doctorId));
        
    //         model.addAttribute("view", "homePage/homeComponent/bookAppointment");
    //         model.addAttribute("file", "bookAppointment");
    //         model.addAttribute("nav", "homePage/partials/navLogged");
    //         model.addAttribute("navState", "navLogged");
    //         model.addAttribute("doctor", doctorRespone);
    //         model.addAttribute("listDepartmentRespones", listDepartmentRespones);

    //     return result;
    // }

    @GetMapping("/doctors/today/{departmentId}")
    public String getListDoctorOfDepartmentToday(@PathVariable String departmentId, Model model) {

        List<doctorRespone> listDoctorResponeToday = _departmentServices.listDoctorToday(Long.parseLong(departmentId));
        List<departmentRespone> listDepartmentRespones =  _departmentServices.getAllDepartmentRespones();

        model.addAttribute("view", "homePage/homeComponent/fillterDoctorPage");
        model.addAttribute("file", "fillterDoctorPage");
        model.addAttribute("nav", "homePage/partials/navLogged");
        model.addAttribute("navState", "navLogged");
        model.addAttribute("listDoctorOfDepartment", listDoctorResponeToday);
        model.addAttribute("listDepartmentRespones", listDepartmentRespones);

        return "homePage/homeComponent/listDoctor/listDoctorComponent";
    }

    @GetMapping("/doctors/tomorrow/{departmentId}")
    public String getListDoctorOfDepartmentTomorrow(@PathVariable String departmentId, Model model) {

        List<doctorRespone> listDoctorResponeTomorrow = _departmentServices.listDoctorTomorrow(Long.parseLong(departmentId));
        List<departmentRespone> listDepartmentRespones =  _departmentServices.getAllDepartmentRespones();

        model.addAttribute("view", "homePage/homeComponent/fillterDoctorPage");
        model.addAttribute("file", "fillterDoctorPage");
        model.addAttribute("nav", "homePage/partials/navLogged");
        model.addAttribute("navState", "navLogged");
        model.addAttribute("listDoctorOfDepartment", listDoctorResponeTomorrow);
        model.addAttribute("listDepartmentRespones", listDepartmentRespones);

        return "homePage/homeComponent/listDoctor/listDoctorComponent";
    }

    @GetMapping("/doctors/nextsevenday/{departmentId}")
    public String getListDoctorOfDepartmentNextSevenDay(@PathVariable String departmentId, Model model) {

        List<doctorRespone> listDoctorResponeNextSevenDay = _departmentServices.listDoctorNextSevenDay(Long.parseLong(departmentId));
        List<departmentRespone> listDepartmentRespones =  _departmentServices.getAllDepartmentRespones();

        model.addAttribute("view", "homePage/homeComponent/fillterDoctorPage");
        model.addAttribute("file", "fillterDoctorPage");
        model.addAttribute("nav", "homePage/partials/navLogged");
        model.addAttribute("navState", "navLogged");
        model.addAttribute("listDoctorOfDepartment", listDoctorResponeNextSevenDay);
        model.addAttribute("listDepartmentRespones", listDepartmentRespones);

        return "homePage/homeComponent/listDoctor/listDoctorComponent";
    }

    @GetMapping("/doctor/schedules/{doctorId}")
    @ResponseBody
    public List<scheduleRespone> getListScheduleOfDoctor(@PathVariable String doctorId) {
        return _doctorServices.getListScheduleResponsesOfDoctor(Long.parseLong(doctorId));
    }
    
}
