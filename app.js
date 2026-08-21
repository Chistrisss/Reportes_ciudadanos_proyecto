
console.log('JavaScript conectado correctamente');


const sector = 'Localidad Kennedy';
let reportesCriticos = 2;
let reportesMenores = 1;


function calcularTotalReportes(criticos, menores) {
  return criticos + menores;
}

function evaluarFlujo(total) {
  if (total > 6) {
    return 'Alto flujo de reportes';
  } else {
    return 'Flujo normal de reportes';
  }
}

function actualizarPantalla() {
  const totalReportes = calcularTotalReportes(reportesCriticos, reportesMenores);
  const estadoFlujo = evaluarFlujo(totalReportes);

  const mensajeFinal = `Sector: ${sector} | Total de reportes: ${totalReportes} | Prioridad: ${estadoFlujo}`;
  document.getElementById('resultado').textContent = mensajeFinal;
}

actualizarPantalla();


const formulario = document.getElementById('formulario-reporte');

formulario.addEventListener('submit', function(e) {
  e.preventDefault(); 


  reportesCriticos++;


  actualizarPantalla();

  document.getElementById('descripcion').value = '';
});