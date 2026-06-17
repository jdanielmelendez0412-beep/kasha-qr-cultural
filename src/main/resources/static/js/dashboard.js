async function cargarDashboard() {
    try {
        const res = await fetch("/api/estadisticas");
        const data = await res.json();

        document.getElementById("totalVisitas").textContent = data.total;
        document.getElementById("edadPredominante").textContent = data.edad_predominante;
        document.getElementById("procedenciaPredominante").textContent = data.procedencia_predominante;
        document.getElementById("tipoPredominante").textContent = data.tipo_predominante;

        const colores = [
            "#667eea", "#764ba2", "#f093fb", "#4facfe",
            "#43e97b", "#fa709a", "#a18cd1", "#fbc2eb",
            "#84fab0", "#ffecd2", "#fcb69f", "#a1c4fd"
        ];

        function crearChart(id, tipo, labels, valores, label, coloresUsados) {
            const ctx = document.getElementById(id).getContext("2d");
            new Chart(ctx, {
                type: tipo,
                data: {
                    labels: labels,
                    datasets: [{
                        label: label,
                        data: valores,
                        backgroundColor: coloresUsados || colores.slice(0, labels.length),
                        borderColor: "#fff",
                        borderWidth: 2
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            position: "bottom",
                            labels: { boxWidth: 12, padding: 8 }
                        }
                    }
                }
            });
        }

        function colorear(labels) {
            return labels.map((_, i) => colores[i % colores.length]);
        }

        const edadLabels = data.edades.map(e => e.edad);
        const edadValores = data.edades.map(e => e.cantidad);
        crearChart("chartEdad", "bar", edadLabels, edadValores, "Visitantes", colorear(edadLabels));

        const procLabels = data.procedencias.map(p => p.procedencia);
        const procValores = data.procedencias.map(p => p.cantidad);
        crearChart("chartProcedencia", "pie", procLabels, procValores, "Procedencia", colorear(procLabels));

        const tipoLabels = data.tipos.map(t => t.tipo_visitante);
        const tipoValores = data.tipos.map(t => t.cantidad);
        crearChart("chartTipo", "doughnut", tipoLabels, tipoValores, "Tipo de visitante", colorear(tipoLabels));

        const fechaLabels = data.visitas_por_fecha.map(f => f.fecha);
        const fechaValores = data.visitas_por_fecha.map(f => f.cantidad);
        crearChart("chartFecha", "line", fechaLabels, fechaValores, "Visitas");

        function llenarTabla(tablaId, datos, campo1, campo2) {
            const tbody = document.querySelector(`#${tablaId} tbody`);
            const total = datos.reduce((s, d) => s + d.cantidad, 0) || 1;
            tbody.innerHTML = datos.map(d => `
                <tr>
                    <td>${d[campo1]}</td>
                    <td>${d.cantidad}</td>
                    <td>${((d.cantidad / total) * 100).toFixed(1)}%</td>
                </tr>
            `).join("");
        }

        llenarTabla("tablaEdad", data.edades, "edad", "cantidad");
        llenarTabla("tablaProcedencia", data.procedencias, "procedencia", "cantidad");
        llenarTabla("tablaTipo", data.tipos, "tipo_visitante", "cantidad");

        function llenarTablaCruce(tablaId, datos, campo1, campo2) {
            const tbody = document.querySelector(`#${tablaId} tbody`);
            tbody.innerHTML = datos.map(d => `
                <tr>
                    <td>${d[campo1]}</td>
                    <td>${d[campo2]}</td>
                    <td>${d.cantidad}</td>
                </tr>
            `).join("");
        }

        llenarTablaCruce("tablaEdadProcedencia", data.edad_procedencia, "edad", "procedencia");
        llenarTablaCruce("tablaEdadTipo", data.edad_tipo, "edad", "tipo_visitante");
        llenarTablaCruce("tablaProcedenciaTipo", data.procedencia_tipo, "procedencia", "tipo_visitante");

        document.getElementById("mediaEdad").textContent = data.tendencia_central.media + " años";
        document.getElementById("medianaEdad").textContent = data.tendencia_central.mediana + " años";
        document.getElementById("modaEdad").textContent = data.tendencia_central.moda;
        document.getElementById("modaProcedencia").textContent = data.procedencia_predominante;
        document.getElementById("modaTipo").textContent = data.tipo_predominante;

        llenarTablaCruce("tablaMonumentoProcedencia", data.monumento_procedencia, "monumento", "procedencia");
        llenarTablaCruce("tablaMonumentoEdad", data.monumento_edad, "monumento", "edad");

    } catch (e) {
        console.error("Error cargando estadísticas:", e);
    }
}

document.addEventListener("DOMContentLoaded", cargarDashboard);
