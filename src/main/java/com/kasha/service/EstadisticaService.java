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
