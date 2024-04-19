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

// Redirigir a url dentro del dominio
function redirect(url) {
    var origen = window.location.origin;
    window.location.href = origen + url
}

// Redirigir a url fuera del dominio
function redirectExternal(url) {
    window.location.href = url
}

// Añade una cookie para ver que se ha registrado correctamente
function exitoRegistro() {
    // valor aleatorio para la cookie
    var randomValue = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);

    // Define la fecha de expiración en 2 minutos
    var expirationDate = new Date();
    expirationDate.setTime(expirationDate.getTime() + (2 * 60 * 1000));

    document.cookie = "success=" + randomValue + "; expires=" + expirationDate.toUTCString() + "; path=/";
}

// Comprueba si existe la cookie success
function comprobarExito() {
    // Obtiene todas las cookies del navegador
    var cookies = document.cookie.split(';');

    for(var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();
        if (cookie.indexOf('success=') === 0) {
            return true;
        }
    }
    return false;
}

// Cambiar texto del Title
function cambiarTitle(texto) {
    // Seleccionar el elemento 'title' dentro del 'head'
    var titleElement = document.querySelector('head title');

    // Cambiar el texto del título
    titleElement.innerText = texto;
}