package com.kasha.service;

import com.kasha.model.Visita;
import com.kasha.repository.VisitaRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EstadisticaService {

    private final VisitaRepository repository;

    public EstadisticaService(VisitaRepository repository) {
        this.repository = repository;
    }

    public long getTotalVisitas() {
        return repository.count();
    }

    public List<Map<String, Object>> getEdades() {
        return toMapList(repository.contarPorEdad(), "edad", "cantidad");
    }

    public List<Map<String, Object>> getProcedencias() {
        return toMapList(repository.contarPorProcedencia(), "procedencia", "cantidad");
    }

    public List<Map<String, Object>> getTipos() {
        return toMapList(repository.contarPorTipoVisitante(), "tipo_visitante", "cantidad");
    }

    public List<Map<String, Object>> getVisitasPorFecha() {
        return toMapList(repository.contarPorFecha(), "fecha", "cantidad");
    }

    public String getEdadPredominante() {
        List<Map<String, Object>> edades = getEdades();
        return edades.isEmpty() ? "N/A" : (String) edades.get(0).get("edad");
    }

    public String getProcedenciaPredominante() {
        List<Map<String, Object>> procs = getProcedencias();
        return procs.isEmpty() ? "N/A" : (String) procs.get(0).get("procedencia");
    }

    public String getTipoPredominante() {
        List<Map<String, Object>> tipos = getTipos();
        return tipos.isEmpty() ? "N/A" : (String) tipos.get(0).get("tipo_visitante");
    }

    public List<Map<String, Object>> getCruceEdadProcedencia() {
        return toCruceList(repository.cruceEdadProcedencia(), "edad", "procedencia");
    }

    public List<Map<String, Object>> getCruceEdadTipo() {
        return toCruceList(repository.cruceEdadTipo(), "edad", "tipo_visitante");
    }

    public List<Map<String, Object>> getCruceProcedenciaTipo() {
        return toCruceList(repository.cruceProcedenciaTipo(), "procedencia", "tipo_visitante");
    }

    public List<Map<String, Object>> getCruceMonumentoProcedencia() {
        return toCruceList(repository.cruceMonumentoProcedencia(), "monumento", "procedencia");
    }

    public List<Map<String, Object>> getCruceMonumentoEdad() {
        return toCruceList(repository.cruceMonumentoEdad(), "monumento", "edad");
    }

    public Map<String, Object> getTendenciaCentral() {
        List<Visita> visitas = repository.findAll();
        Map<String, Object> result = new LinkedHashMap<>();

        if (visitas.isEmpty()) {
            result.put("media", "N/A");
            result.put("mediana", "N/A");
            result.put("moda", "N/A");
            return result;
        }

        List<Double> valores = new ArrayList<>();
        for (Visita v : visitas) {
            valores.add(edadToNumero(v.getEdad()));
        }
        Collections.sort(valores);

        double suma = 0;
        Map<Double, Long> freq = new HashMap<>();
        for (double v : valores) {
            suma += v;
            freq.merge(v, 1L, Long::sum);
        }

        double media = suma / valores.size();
        double mediana = valores.size() % 2 == 0
            ? (valores.get(valores.size() / 2 - 1) + valores.get(valores.size() / 2)) / 2.0
            : valores.get(valores.size() / 2);
        double moda = freq.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();

        result.put("media", String.format("%.1f", media));
        result.put("mediana", String.format("%.1f", mediana));
        result.put("moda", numeroToEdad((int) Math.round(moda)));
        return result;
    }

    private double edadToNumero(String edad) {
        switch (edad) {
            case "Menor de 18 años": return 15;
            case "18 a 25 años": return 21.5;
            case "26 a 35 años": return 30.5;
            case "36 a 45 años": return 40.5;
            case "46 a 60 años": return 53;
            case "Mayor de 60 años": return 65;
            default: return 30;
        }
    }

    private String numeroToEdad(int n) {
        if (n < 18) return "Menor de 18 años";
        if (n <= 25) return "18 a 25 años";
        if (n <= 35) return "26 a 35 años";
        if (n <= 45) return "36 a 45 años";
        if (n <= 60) return "46 a 60 años";
        return "Mayor de 60 años";
    }

    private List<Map<String, Object>> toMapList(List<Object[]> rows, String key1, String key2) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(key1, row[0]);
            map.put(key2, row[1]);
            result.add(map);
        }
        return result;
    }

    private List<Map<String, Object>> toCruceList(List<Object[]> rows, String key1, String key2) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(key1, row[0]);
            map.put(key2, row[1]);
            map.put("cantidad", row[2]);
            result.add(map);
        }
        return result;
    }
}
