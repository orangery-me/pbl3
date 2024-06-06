package com.nhom10.pbl.controller;

import java.util.List;

import com.nhom10.pbl.payload.response.ArticleResponse;
import com.nhom10.pbl.security.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.nhom10.pbl.payload.response.UserResponse;
import com.nhom10.pbl.payload.response.departmentRespone;
import com.nhom10.pbl.security.service.AuthenticateService;
import com.nhom10.pbl.services.departmentServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final departmentServices departmentServices;
    private final AuthenticateService authenticateService;
    private final ArticleService articleService;

    @GetMapping("/home")
    public String getHomePage(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        if (user == null) {
            model.addAttribute("view", "homePage/homeComponent/homePage");
            model.addAttribute("file", "homePage");
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
        } else {
            List<departmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("view", "homePage/homeComponent/homePage");
            model.addAttribute("file", "homePage");
            model.addAttribute("nav", "homePage/partials/navLogged");
            model.addAttribute("navState", "navLogged");
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        }
        return "homePage/index";
    }

//    @RequestMapping("/latest-articles")
//    public String getLatestArticles(Model model) {
////        List<ArticleResponse> latestArticles = articleService.getLatestArticles();
////        model.addAttribute("latestArticles", latestArticles);
//        return "articles/news";  // Ensure this points to the correct Thymeleaf template
//    }

    @GetMapping("/article/{id}")
    public String getArticleDetail(@PathVariable Long id, Model model) {
        ArticleResponse article = articleService.findArticleById(id);
        model.addAttribute("article", article);
        return "articles/detail";
    }

    @RequestMapping("/login")
    public String login() {
        return "auth/login/login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "homePage/index";
    }

    @RequestMapping("/admin")
    public String adminPage() {
        return "admin/pages/home";
    }

    @RequestMapping("/admin/accounts")
    public String adminControllUsers() {
        return "admin/pages/accounts";
    }

    @RequestMapping("/admin/articles")
    public String adminControllArticles() {
        return "admin/pages/articles";
    }

    @RequestMapping("/new-article")
    public String newArticle(Model model, HttpServletRequest request){
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "articles/index";
    }

    @RequestMapping("/news")
    public String news(Model model, HttpServletRequest request){
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        if (user == null) {
            model.addAttribute("view", "homePage/homeComponent/homePage");
            model.addAttribute("file", "homePage");
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
        } else {
            List<departmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("view", "homePage/homeComponent/homePage");
            model.addAttribute("file", "homePage");
            model.addAttribute("nav", "homePage/partials/navLogged");
            model.addAttribute("navState", "navLogged");
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        }
        return "articles/news";
    }

}
