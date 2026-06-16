package com.kasha.controller;

import com.kasha.model.Visita;
import com.kasha.repository.VisitaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class WebController {

    private final VisitaRepository repository;

    public WebController(VisitaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/formulario")
    public String formulario() {
        return "formulario";
    }

    @PostMapping("/registrar")
    public String registrar(
            @RequestParam String edad,
            @RequestParam String procedencia,
            @RequestParam("tipo_visitante") String tipoVisitante) {

        Visita visita = new Visita("Ka'sha - Tambor Wayuu", edad, procedencia, tipoVisitante);
        repository.save(visita);
        return "redirect:/monumento";
    }

    @GetMapping("/monumento")
    public String monumento() {
        return "monumento";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("visitas", repository.findAllByOrderByIdDesc());
        return "admin";
    }
}
