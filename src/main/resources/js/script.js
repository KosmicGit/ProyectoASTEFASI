// Establecer Cookie de Sesion
function guardarCookie(valor) {

    var expirationDate = new Date();
    expirationDate.setTime(expirationDate.getTime() + (30 * 60 * 1000));
    document.cookie = 'sesion=' + encodeURIComponent(valor) + ';expires=' + expirationDate.toUTCString() + ';path=/';
}

// Añade una cookie para ver que se ha registrado correctamente
function exitoRegistro(valor) {

    var expirationDate = new Date();
    expirationDate.setTime(expirationDate.getTime() + (2 * 60 * 1000));
    document.cookie = "success=" + encodeURIComponent(valor) + "; expires=" + expirationDate.toUTCString() + "; path=/";
}


// Obtener valor de Sesion
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

// Comprueba si existe la cookie success
function comprobarCookie(nombre) {
    var cookies = document.cookie.split(';');

    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();
        if (cookie.indexOf(nombre + '=') === 0) {
            return true;
        }
    }
    return false;
}
