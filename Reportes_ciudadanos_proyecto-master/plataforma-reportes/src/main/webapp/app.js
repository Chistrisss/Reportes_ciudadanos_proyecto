
document.addEventListener("DOMContentLoaded", function() {


  let mapa = null;
  let coordenadasSeleccionadas = null;
  let marcadorTemporal = null;
  const mapaContenedor = document.getElementById('mapa-interactivo');


  if (mapaContenedor && typeof L !== 'undefined') {
    mapa = L.map('mapa-interactivo').setView([4.6097, -74.0817], 11);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap'
    }).addTo(mapa);

    mapa.on('click', function(e) {
      coordenadasSeleccionadas = e.latlng;
      if (marcadorTemporal) {
        mapa.removeLayer(marcadorTemporal);
      }
      marcadorTemporal = L.marker(coordenadasSeleccionadas).addTo(mapa);

      const textoCoords = document.getElementById('texto-coordenadas');
      if (textoCoords) {
        textoCoords.textContent = '📍 Ubicación fijada para el reporte.';
        textoCoords.style.color = 'green';
      }
    });
  }


  const modal = document.getElementById('modal-login');
  const btnLoginNav = document.getElementById('btn-login');
  const btnCerrarModal = document.getElementById('btn-cerrar-modal');
  const btnConfirmarLogin = document.getElementById('btn-confirmar-login');
  const usuarioInput = document.getElementById('usuario-input');
  const claveInput = document.getElementById('clave-input');


  if (usuarioInput) {
    const cookieUsuario = document.cookie.split('; ').find(c => c.startsWith('ultimoUsuario='));
    if (cookieUsuario) {
      usuarioInput.value = decodeURIComponent(cookieUsuario.split('=')[1]);
    }
  }

  if (btnLoginNav && modal) {
    btnLoginNav.addEventListener('click', function() {
      modal.classList.remove('oculto');
    });
  }

  if (btnCerrarModal && modal) {
    btnCerrarModal.addEventListener('click', function() {
      modal.classList.add('oculto');
      if (claveInput) claveInput.value = '';
    });
  }

  if (btnConfirmarLogin) {
    btnConfirmarLogin.addEventListener('click', async function() {
      const datos = new URLSearchParams();
      datos.append('usuario', usuarioInput.value);
      datos.append('clave', claveInput.value);

      try {
        const respuesta = await fetch('login', {
          method: 'POST',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          credentials: 'same-origin',
          body: datos
        });

        if (respuesta.url.includes('login.jsp')) {
          const params = new URL(respuesta.url).searchParams;
          const errorMsg = params.get('error') === '2' ? 'Usuario o clave incorrectos.' : 'Error al iniciar sesión.';
          alert(errorMsg);
          return;
        }

        if (btnLoginNav) btnLoginNav.textContent = 'Sesión Iniciada ✓';
        if (modal) modal.classList.add('oculto');
        if (claveInput) claveInput.value = '';
        alert('¡Sesión iniciada correctamente!');
      } catch (error) {
        alert('No se pudo conectar con el servidor.');
      }
    });
  }


  const formulario = document.getElementById('formulario-reporte');

  if (formulario) {
    formulario.addEventListener('submit', async function(e) {
      e.preventDefault();

      if (!coordenadasSeleccionadas) {
        alert('Por favor, haz clic en el mapa de Bogotá para fijar la ubicación del problema.');
        return;
      }

      const sectorInput = document.getElementById('sector').value.trim();
      const selectElement = document.getElementById('tipo');
      const problemaInput = selectElement.value;
      const descripcionInput = document.getElementById('descripcion').value;

      const datos = new URLSearchParams();
      datos.append('barrio', sectorInput);
      datos.append('titulo', problemaInput);
      datos.append('descripcion', descripcionInput);
      datos.append('latitud', coordenadasSeleccionadas.lat.toFixed(6));
      datos.append('longitud', coordenadasSeleccionadas.lng.toFixed(6));

      try {
        const respuesta = await fetch('ReporteServlet', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-Requested-With': 'fetch'
          },
          credentials: 'same-origin',
          body: datos
        });

        if (!respuesta.ok) {
          const mensaje = await respuesta.text();
          alert(mensaje);
          if (respuesta.status === 401) {
            if (btnLoginNav) btnLoginNav.textContent = 'Iniciar Sesión';
            if (modal) modal.classList.remove('oculto');
          }
          return;
        }

        alert('¡Alerta publicada! Redirigiendo a la lista de reportes...');
        window.location.href = 'reportes-dinamicos.jsp';

      } catch (error) {
        alert('Error grave: No se pudo conectar con el servidor.');
      }
    });
  }
});