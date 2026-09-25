const mapa = L.map('mapa-interactivo').setView([4.6097, -74.0817], 11);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  attribution: '© OpenStreetMap'
}).addTo(mapa);

let coordenadasSeleccionadas = null;
let marcadorTemporal = null;

mapa.on('click', function(e) {
  coordenadasSeleccionadas = e.latlng;
  if (marcadorTemporal) {
    mapa.removeLayer(marcadorTemporal);
  }
  marcadorTemporal = L.marker(coordenadasSeleccionadas).addTo(mapa);
  document.getElementById('texto-coordenadas').textContent = '📍 Ubicación seleccionada en el mapa.';
  document.getElementById('texto-coordenadas').style.color = 'green';
});

class Reporte {
  constructor(sector, categoriaPrincipal, problemaEspecifico, descripcion, lat, lng) {
    this.id = Date.now();
    this.sector = sector;
    this.categoriaPrincipal = categoriaPrincipal;
    this.problemaEspecifico = problemaEspecifico;
    this.descripcion = descripcion;
    this.lat = lat;
    this.lng = lng;
    this.fecha = new Date().toLocaleTimeString();
    this.estado = 'Pendiente';

    this.clasePin = 'pin-rojo';
    this.claseTarjeta = 'tarjeta-roja';

    if (this.categoriaPrincipal.includes('Amarillo')) {
      this.clasePin = 'pin-amarillo';
      this.claseTarjeta = 'tarjeta-amarilla';
    } else if (this.categoriaPrincipal.includes('Verde')) {
      this.clasePin = 'pin-verde';
      this.claseTarjeta = 'tarjeta-verde';
    } else if (this.categoriaPrincipal.includes('Azul')) {
      this.clasePin = 'pin-azul';
      this.claseTarjeta = 'tarjeta-azul';
    }
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

const modal = document.getElementById('modal-login');
const btnLoginNav = document.getElementById('btn-login');
const btnCerrarModal = document.getElementById('btn-cerrar-modal');
const btnConfirmarLogin = document.getElementById('btn-confirmar-login');
const claveInput = document.getElementById('clave-input');

btnLoginNav.addEventListener('click', function() {
  modal.classList.remove('oculto');
});

btnCerrarModal.addEventListener('click', function() {
  modal.classList.add('oculto');
  claveInput.value = '';
});

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

  if (!usuarioAutenticado) {
    alert('Debes iniciar sesión para publicar. Por favor ingresa la clave en la ventana.');
    modal.classList.remove('oculto');
    return;
  }

  if (!coordenadasSeleccionadas) {
    alert('Haz clic en el mapa de Bogotá para elegir la ubicación antes de enviar.');
    return;
  }

  const sectorInput = document.getElementById('sector').value.trim();
  const selectElement = document.getElementById('tipo');
  const problemaInput = selectElement.value;
  const categoriaInput = selectElement.options[selectElement.selectedIndex].parentNode.label;
  const descripcionInput = document.getElementById('descripcion').value;

  if (!localidadesValidas.includes(sectorInput)) {
    alert('Por favor selecciona una localidad válida de la lista sugerida.');
    return;
  }

  const nuevoReporte = new Reporte(
    sectorInput, 
    categoriaInput, 
    problemaInput, 
    descripcionInput, 
    coordenadasSeleccionadas.lat, 
    coordenadasSeleccionadas.lng
  );
  
  listaDeReportes.push(nuevoReporte);

  const iconoPersonalizado = L.divIcon({
    className: nuevoReporte.clasePin,
    iconSize: [20, 20],
    iconAnchor: [10, 10]
  });

  const marcadorDefinitivo = L.marker([nuevoReporte.lat, nuevoReporte.lng], { icon: iconoPersonalizado }).addTo(mapa);
  
  marcadorDefinitivo.bindPopup(`
    <strong style="color: #333;">${nuevoReporte.problemaEspecifico}</strong><br>
    <em>${nuevoReporte.sector}</em><br>
    <p style="margin: 5px 0;">${nuevoReporte.descripcion}</p>
    <small>Registrado a las: ${nuevoReporte.fecha}</small>
  `);

  actualizarPantalla();
  formulario.reset();
  
  if (marcadorTemporal) {
    mapa.removeLayer(marcadorTemporal);
  }
  coordenadasSeleccionadas = null;
  document.getElementById('texto-coordenadas').textContent = 'Haz clic en el mapa para fijar el punto.';
  document.getElementById('texto-coordenadas').style.color = 'red';
});

function actualizarPantalla() {
  document.getElementById('resultado').textContent = 
    `Total de reportes registrados: ${listaDeReportes.length}`;

  const contenedor = document.getElementById('lista-reportes');
  contenedor.innerHTML = '';

  listaDeReportes.forEach(function(reporte) {
    const tarjeta = document.createElement('div');
    tarjeta.className = `tarjeta-reporte ${reporte.claseTarjeta}`;

    tarjeta.innerHTML = `
      <h3>${reporte.sector} - <small style="font-weight:normal;">${reporte.categoriaPrincipal}</small></h3>
      <strong>${reporte.problemaEspecifico}</strong>
      <p style="margin: 5px 0;">${reporte.descripcion}</p>
      <small><strong>Hora:</strong> ${reporte.fecha} | <strong>Estado:</strong> ${reporte.estado}</small>
    `;

    contenedor.appendChild(tarjeta);
  });
}