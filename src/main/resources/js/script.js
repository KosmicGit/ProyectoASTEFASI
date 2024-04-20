// Establecer Cookie de Sesion
function guardarCookie(valor) {
    var ahora = new Date();
    var tiempoExpiracion = ahora.getTime() + 30 * 60 * 1000;
    ahora.setTime(tiempoExpiracion);
    document.cookie = 'sesion=' + encodeURIComponent(valor) + ';expires=' + ahora.toUTCString() + ';path=/';
}

// Añade una cookie para ver que se ha registrado correctamente
function exitoRegistro(valor) {

    var expirationDate = new Date();
    expirationDate.setTime(expirationDate.getTime() + (2 * 60 * 1000));

    document.cookie = "success=" + encodeURIComponent(valor) + "; expires=" + expirationDate.toUTCString() + "; path=/";
}


// Obtener valor de Cookie
function obtenerSesion() {
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

// Obtener valor de Success
function obtenerSuccess() {
    var nombre = 'success' + '=';
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

// Redirigir a url dentro del dominio
function redirect(url) {
    var origen = window.location.origin;
    window.location.href = origen + url
}

// Redirigir a url fuera del dominio
function redirectExternal(url) {
    window.location.href = url
}

// Comprueba si existe la cookie success
function comprobarExito() {
    // Obtiene todas las cookies del navegador
    var cookies = document.cookie.split(';');

    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();
        if (cookie.indexOf('success=') === 0) {
            return true;
        }
    }
    return false;
}