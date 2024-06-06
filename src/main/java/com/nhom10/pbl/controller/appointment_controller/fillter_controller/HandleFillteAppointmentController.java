package com.nhom10.pbl.controller.appointment_controller.fillter_controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

import com.nhom10.pbl.payload.response.BookingModel;
import com.nhom10.pbl.payload.resquest.ScheduleRequest;
import com.nhom10.pbl.payload.response.DepartmentRespone;
import com.nhom10.pbl.payload.response.DoctorRespone;
import com.nhom10.pbl.payload.response.ScheduleRespone;
import com.nhom10.pbl.services.DepartmentServices;
import com.nhom10.pbl.services.DoctorServices;

@Controller
@RequestMapping("home/user/appointment")
public class HandleFillteAppointmentController {

    private final DepartmentServices _departmentServices;
    private final DoctorServices _doctorServices;

    public HandleFillteAppointmentController(
            DepartmentServices departmentServices,
            DoctorServices doctorServices) {
        this._departmentServices = departmentServices;
        this._doctorServices = doctorServices;
    }

    @GetMapping("/{departmentId}")
    @ResponseBody
    public DepartmentRespone getDepartment(@PathVariable String departmentId) {

        return _departmentServices.getDepartmentByID(Long.parseLong(departmentId));
    }

    @GetMapping("/doctors/{departmentId}")
    public String getListDoctorOfDepartment(@PathVariable String departmentId, Model model) {
        List<DoctorRespone> ListdoctorResponses = _departmentServices.getListDoctor(Long.parseLong(departmentId),
                false);
        List<DepartmentRespone> listDepartmentRespones = _departmentServices.getAllDepartmentRespones();

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

        return "homePage/index";
    }

    // @GetMapping("/doctor/{doctorId}")
    // @ResponseBody
    // public List<bookingModel> getDetailDoctor(@PathVariable String doctorId,
    // Model model) {
    // doctorRespone doctorRespone =
    // _doctorServices.getDoctorResponeById(Long.parseLong(doctorId));
    // List<departmentRespone> listDepartmentRespones =
    // _departmentServices.getAllDepartmentRespones();

    // List<bookingModel> result =
    // _doctorServices.getListBookingModelsOfDoctor(Long.parseLong(doctorId));

    // model.addAttribute("view", "homePage/homeComponent/bookAppointment");
    // model.addAttribute("file", "bookAppointment");
    // model.addAttribute("nav", "homePage/partials/navLogged");
    // model.addAttribute("navState", "navLogged");
    // model.addAttribute("doctor", doctorRespone);
    // model.addAttribute("listDepartmentRespones", listDepartmentRespones);

    // return result;
    // }

    @GetMapping("/doctors/today/{departmentId}")
    public String getListDoctorOfDepartmentToday(@PathVariable String departmentId, Model model) {

        List<DoctorRespone> listDoctorResponeToday = _departmentServices.listDoctorToday(Long.parseLong(departmentId));
        List<DepartmentRespone> listDepartmentRespones = _departmentServices.getAllDepartmentRespones();

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

        List<DoctorRespone> listDoctorResponeTomorrow = _departmentServices
                .listDoctorTomorrow(Long.parseLong(departmentId));
        List<DepartmentRespone> listDepartmentRespones = _departmentServices.getAllDepartmentRespones();

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

        List<DoctorRespone> listDoctorResponeNextSevenDay = _departmentServices
                .listDoctorNextSevenDay(Long.parseLong(departmentId));
        List<DepartmentRespone> listDepartmentRespones = _departmentServices.getAllDepartmentRespones();

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
    public List<ScheduleRespone> getListScheduleOfDoctor(@PathVariable String doctorId) {
        return _doctorServices.getListScheduleResponsesOfDoctor(Long.parseLong(doctorId));
    }

}
