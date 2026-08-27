class Reporte {
  constructor(sector, tipo, descripcion) {
    this.id = Date.now();
    this.sector = sector;
    this.tipo = tipo;
    this.descripcion = descripcion;
    this.fecha = new Date().toLocaleTimeString();
    this.estado = 'Pendiente';
  }

  obtenerResumen() {
    return `${this.sector} - ${this.tipo}: ${this.descripcion}`;
  }
}

const listaDeReportes = [];
let usuarioAutenticado = false;

const localidadesValidas = [
  "Usaquén", "Chapinero", "Santa Fe", "San Cristóbal", "Usme",
  "Tunjuelito", "Bosa", "Kennedy", "Fontibón", "Engativá",
  "Suba", "Barrios Unidos", "Teusaquillo", "Los Mártires",
  "Antonio Nariño", "Puente Aranda", "La Candelaria",
  "Rafael Uribe Uribe", "Ciudad Bolívar", "Sumapaz"
];

// Elementos del Modal
const modal = document.getElementById('modal-login');
const btnLoginNav = document.getElementById('btn-login');
const btnCerrarModal = document.getElementById('btn-cerrar-modal');
const btnConfirmarLogin = document.getElementById('btn-confirmar-login');
const claveInput = document.getElementById('clave-input');

// Abrir el modal desde el botón del menú
btnLoginNav.addEventListener('click', function() {
  modal.classList.remove('oculto');
});

// Cerrar el modal con el botón Cancelar
btnCerrarModal.addEventListener('click', function() {
  modal.classList.add('oculto');
  claveInput.value = '';
});

// Procesar la clave ingresada
btnConfirmarLogin.addEventListener('click', function() {
  if (claveInput.value === '1234') {
    usuarioAutenticado = true;
    btnLoginNav.textContent = 'Sesión Iniciada ✓';
    modal.classList.add('oculto');
    claveInput.value = '';
    alert('¡Sesión iniciada correctamente!');
  } else {
    alert('Clave incorrecta.');
  }
});

const formulario = document.getElementById('formulario-reporte');

formulario.addEventListener('submit', function(e) {
  e.preventDefault();

  // Si no ha iniciado sesión, despliega la ventana de login automáticamente
  if (!usuarioAutenticado) {
    alert('Debes iniciar sesión para publicar. Por favor ingresa la clave en la ventana.');
    modal.classList.remove('oculto');
    return;
  }

  const sectorInput = document.getElementById('sector').value.trim();
  const tipoInput = document.getElementById('tipo').value;
  const descripcionInput = document.getElementById('descripcion').value;

  if (!localidadesValidas.includes(sectorInput)) {
    alert('Por favor selecciona una localidad válida de la lista sugerida.');
    return;
  }

  const nuevoReporte = new Reporte(sectorInput, tipoInput, descripcionInput);
  listaDeReportes.push(nuevoReporte);

  actualizarPantalla();
  formulario.reset();
});

function actualizarPantalla() {
  document.getElementById('resultado').textContent = 
    `Total de reportes registrados: ${listaDeReportes.length}`;

  const contenedor = document.getElementById('lista-reportes');
  contenedor.innerHTML = '';

  listaDeReportes.forEach(function(reporte) {
    const tarjeta = document.createElement('div');
    tarjeta.className = 'tarjeta-reporte';

    tarjeta.innerHTML = `
      <h3>${reporte.sector} - <small>${reporte.tipo}</small></h3>
      <p>${reporte.descripcion}</p>
      <small><strong>Hora:</strong> ${reporte.fecha} | <strong>Estado:</strong> ${reporte.estado}</small>
    `;

    contenedor.appendChild(tarjeta);
  });
}