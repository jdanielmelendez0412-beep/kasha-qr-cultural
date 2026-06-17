package com.kasha.controller;

import com.kasha.model.Visita;
import com.kasha.repository.ContadorRepository;
import com.kasha.repository.VisitaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    private final VisitaRepository repository;
    private final ContadorRepository contadorRepository;

    public WebController(VisitaRepository repository, ContadorRepository contadorRepository) {
        this.repository = repository;
        this.contadorRepository = contadorRepository;
    }

    @GetMapping("/")
    public String index() {
        contadorRepository.findById(1L).ifPresent(c -> {
            c.setTotalVisitas(c.getTotalVisitas() + 1);
            contadorRepository.save(c);
        });
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

}
