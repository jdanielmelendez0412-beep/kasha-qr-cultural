package com.kasha.controller;

import com.kasha.model.Visita;
import com.kasha.repository.VisitaRepository;
import com.kasha.service.EstadisticaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@RestController
public class ApiController {

    private final EstadisticaService service;
    private final VisitaRepository repository;

    public ApiController(EstadisticaService service, VisitaRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/api/estadisticas")
    public Map<String, Object> estadisticas() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("total", service.getTotalVisitas());
        data.put("edad_predominante", service.getEdadPredominante());
        data.put("procedencia_predominante", service.getProcedenciaPredominante());
        data.put("tipo_predominante", service.getTipoPredominante());
        data.put("edades", service.getEdades());
        data.put("procedencias", service.getProcedencias());
        data.put("tipos", service.getTipos());
        data.put("visitas_por_fecha", service.getVisitasPorFecha());
        data.put("edad_procedencia", service.getCruceEdadProcedencia());
        data.put("edad_tipo", service.getCruceEdadTipo());
        data.put("procedencia_tipo", service.getCruceProcedenciaTipo());
        return data;
    }

    @GetMapping("/api/exportar")
    public ResponseEntity<byte[]> exportar(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            return ResponseEntity.status(403).body("Acceso denegado".getBytes());
        }
        List<Visita> visitas = repository.findAllByOrderByIdDesc();
        StringBuilder csv = new StringBuilder();
        csv.append("ID,Fecha,Hora,Monumento,Edad,Procedencia,Tipo Visitante\n");
        for (Visita v : visitas) {
            csv.append(v.getId()).append(",");
            csv.append(v.getFecha()).append(",");
            csv.append(v.getHora()).append(",");
            csv.append("\"").append(v.getMonumento()).append("\",");
            csv.append("\"").append(v.getEdad()).append("\",");
            csv.append("\"").append(v.getProcedencia()).append("\",");
            csv.append("\"").append(v.getTipoVisitante()).append("\"\n");
        }
        byte[] bytes = csv.toString().getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "datos_kasha_" + LocalDate.now() + ".csv");
        return ResponseEntity.ok().headers(headers).body(bytes);
    }
}
