package com.nhom10.pbl.controller.api;

import java.sql.Date;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhom10.pbl.models.Status;
import com.nhom10.pbl.payload.response.ArticleResponse;
import com.nhom10.pbl.payload.response.UserResponse;
import com.nhom10.pbl.payload.request.ArticleRequest;
import com.nhom10.pbl.payload.request.UserDTO;
import com.nhom10.pbl.security.service.UserService;
import com.nhom10.pbl.services.ArticleService;
import com.nhom10.pbl.services.ScheduleServices;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final ArticleService articleService;
    private final ScheduleServices scheduleServices;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @RequestMapping(value = "/getUserById", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponse>> getUserById(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/getUserByUsername", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponse>> getUserByUsername(@RequestParam("username") String username) {
        try {
            return ResponseEntity.ok(userService.getUserByUsername(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/getUserByEmail", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponse>> getUserByEmail(@RequestParam("email") String email) {
        try {
            return ResponseEntity.ok(userService.getUserByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/getUserByPhone", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponse>> getUserByPhone(@RequestParam("phone") String phone) {
        try {
            return ResponseEntity.ok(userService.getUserByPhone(phone));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/createArticle")
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody ArticleRequest article) {
        try {
            return ResponseEntity.ok(articleService.createArticle(article));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getAllArticles")
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @RequestMapping(value = "/getArticleById", method = RequestMethod.GET)
    public ResponseEntity<List<ArticleResponse>> getArticleById(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(articleService.getArticleById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @RequestMapping(value = "/getArticleByTitle", method = RequestMethod.GET)
    public ResponseEntity<List<ArticleResponse>> getArticleByTitle(@RequestParam("title") String title) {
        try {
            return ResponseEntity.ok(articleService.getArticleByTitle(title));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/getArticleByContent", method = RequestMethod.GET)
    public ResponseEntity<List<ArticleResponse>> getArticleByContent(@RequestParam("content") String content) {
        try {
            return ResponseEntity.ok(articleService.getArticleByContent(content));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/getArticleByAuthor", method = RequestMethod.GET)
    public ResponseEntity<List<ArticleResponse>> getArticleByAuthor(@RequestParam("author") String author) {
        try {
            return ResponseEntity.ok(articleService.getArticleByAuthor(author));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/getArticleByStatus", method = RequestMethod.GET)
    public ResponseEntity<List<ArticleResponse>> getArticleByStatus(@RequestParam("status") Status status) {
        try {
            return ResponseEntity.ok(articleService.getArticleByStatus(status));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updateArticle/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable("id") Long id,
            @RequestParam("status") Status status) {
        try {
            return ResponseEntity.ok(articleService.updateStatus(id, status));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/getRevenueOfDay", method = RequestMethod.GET)
    public ResponseEntity<Long> getRevenueOfDay(@RequestParam("date") Date date) {
        try {
            return ResponseEntity.ok(scheduleServices.getRevenueOfDay(date));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/getRevenueOfMonth", method = RequestMethod.GET)
    public ResponseEntity<Long> getRevenueOfDay(@RequestParam("month") int month, @RequestParam("year") int year) {
        try {
            return ResponseEntity.ok(scheduleServices.getRevenueOfMonth(month, year));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
