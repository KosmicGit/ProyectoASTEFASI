// Establecer Cookie
function guardarCookie(valor) {
    var ahora = new Date();
    var tiempoExpiracion = ahora.getTime() + 30 * 60 * 1000;
    ahora.setTime(tiempoExpiracion);
    document.cookie = 'sesion=' + encodeURIComponent(valor) + ';expires=' + ahora.toUTCString() + ';path=/';
}

// Obtener valor de Cookie
function obtenerValorDeSesion() {
    var nombre = 'sesion' + '=';
    var cookies = document.cookie.split(';');
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        while (cookie.charAt(0) == ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(nombre) == 0) {
            return decodeURIComponent(cookie.substring(nombre.length, cookie.length));
        }
    }
    return null;
}

// Redirigir a url
function redirect(url) {
    var origen = window.location.origin;
    var destino = origen + url;
    window.location.href = destino
}
