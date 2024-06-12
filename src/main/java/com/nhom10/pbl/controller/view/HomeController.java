package com.nhom10.pbl.controller.view;

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
        List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
        model.addAttribute("listDepartmentRespones", listDepartmentRespones);

        if (user == null || user.getRole().equals(ERole.ADMIN.name())) {
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
        } else {
            if (user.getRole().equals(ERole.DOCTOR.name())) {
                model.addAttribute("nav", "homePage/partials/navDoctorLogged");
                model.addAttribute("navState", "navDoctorLogged");
            } else if (user.getRole().equals(ERole.PATIENT.name())) {
                model.addAttribute("nav", "homePage/partials/navLogged");
                model.addAttribute("navState", "navLogged");
            }
            model.addAttribute("user", user);
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

    @RequestMapping("/new-article")
    public String newArticle(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "articles/post";
    }

    @RequestMapping("/news")
    public String news(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        model.addAttribute("view", "homePage/homeComponent/homePage");
        model.addAttribute("file", "homePage");
        if (user == null) {

            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
        } else {
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
            if (user.getRole().equals(ERole.DOCTOR.name())) {
                model.addAttribute("nav", "homePage/partials/navDoctorLogged");
                model.addAttribute("navState", "navDoctorLogged");
            } else {
                model.addAttribute("nav", "homePage/partials/navLogged");
                model.addAttribute("navState", "navLogged");
            }
        }
        return "articles/news";
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

    @GetMapping("/huongdan")
    public String huongdan(Model model, HttpServletRequest request) {

        UserResponse user = authenticateService.getUserFromCookie(request);

        model.addAttribute("view", "homePage/homeComponent/medical_examination_process_page");
        model.addAttribute("file", "medical_examination_process_page");

        if (user == null) {
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        } else {
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
    public String BangGia(Model model, HttpServletRequest request) {

        UserResponse user = authenticateService.getUserFromCookie(request);

        model.addAttribute("view", "homePage/homeComponent/priceOfGudmec");
        model.addAttribute("file", "priceOfGudmec");

        if (user == null) {
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        } else {
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
    public String TraCuu(Model model, HttpServletRequest request) {

        UserResponse user = authenticateService.getUserFromCookie(request);

        model.addAttribute("view", "homePage/homeComponent/SearchResult");
        model.addAttribute("file", "SearchResult");

        if (user == null) {
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        } else {
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

    @GetMapping("/muchailong")
    public String muchailong(Model model, HttpServletRequest request) {

        UserResponse user = authenticateService.getUserFromCookie(request);

        if (user == null) {
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        } else {
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

        return "homePage/fixpage/mc-hi-lng";
    }

    @GetMapping("/huongdankham")
    public String huongdankham(Model model, HttpServletRequest request) {

        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("view", "homePage/fixpage/hng-dn-khm-bnh");
        model.addAttribute("file", "huongdankham");

        if (user == null) {
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        } else {
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

    @GetMapping("/huongdanthanhtoanbaohiem")
    public String thanhtoanbaohiem(Model model, HttpServletRequest request) {

        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("view", "homePage/fixpage/hng-dn-thanh-ton-bo-him");
        model.addAttribute("file", "thanhtoanbaohiem");

        if (user == null) {
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        } else {
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

    @GetMapping("/thutucxuatnhapvien")
    public String thutuc(Model model, HttpServletRequest request) {

        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("view", "homePage/fixpage/th-tc-xut-v-nhp-vin");
        model.addAttribute("file", "thutucxuatnhapvien");

        if (user == null) {
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        } else {
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

    @GetMapping("/thongtinkhachthambenh")
    public String thongtin(Model model, HttpServletRequest request) {

        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("view", "homePage/fixpage/thng-tin-cho-khch-thm-bnh");
        model.addAttribute("file", "thongtinkhachthambenh");

        if (user == null) {
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        } else {
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

    @GetMapping("/dieutrinoitru")
    public String dieutri(Model model, HttpServletRequest request) {

        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("view", "homePage/fixpage/hng-dn-khch-hng-iu-tr-ni-tr");
        model.addAttribute("file", "dieutrinoitru");

        if (user == null) {
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        } else {
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

    @GetMapping("/thanhtuu")
    public String thanhtuu(Model model, HttpServletRequest request) {

        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("view", "homePage/fixpage/thnh-tu");
        model.addAttribute("file", "thanhtuu");

        if (user == null) {
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        } else {
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

    @GetMapping("/chuyenkhoa")
    public String chuyenkhoa(Model model, HttpServletRequest request) {

        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("view", "homePage/fixpage/chuyenkhoa");
        model.addAttribute("file", "chuyenkhoa");

        if (user == null) {
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
            List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        } else {
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

        return "homePage/index";
    }
}
