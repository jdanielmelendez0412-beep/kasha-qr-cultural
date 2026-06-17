package com.kasha.controller;

import com.kasha.repository.VisitaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final VisitaRepository repository;

    @Value("${admin.password}")
    private String adminPassword;

    public AdminController(VisitaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/admin/login")
    public String loginForm(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) {
            return "redirect:/admin";
        }
        return "admin-login";
    }

    @PostMapping("/admin/login")
    public String login(@RequestParam String password, HttpServletRequest request) {
        if (adminPassword.equals(password)) {
            request.getSession(true).setAttribute("admin", true);
            return "redirect:/admin";
        }
        return "redirect:/admin/login?error=1";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String admin(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("visitas", repository.findAllByOrderByIdDesc());
        return "admin";
    }
}
