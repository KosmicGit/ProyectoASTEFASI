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

// Eliminar cookie sesion
function cerrarSesion(cookie) {
    document.cookie = cookie + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/";
}

// Mostart notificacion
function mostrarNoti(texto) {
    Toastify({
        text: texto,
        duration: 5000,
        gravity: "top",
        position: "right",
        backgroundColor: "repeating-linear-gradient(45deg, #00d1b2, #00d1b2 10px, #0088a8 10px, #0088a8 20px)"
    }).showToast();
}

//Modal Usuario
document.addEventListener('DOMContentLoaded', () => {
    // Functions to open and close a modal
    function openModal($el) {
        $el.classList.add('is-active');
    }

    function closeModal($el) {
        $el.classList.remove('is-active');
    }

    function closeAllModals() {
        (document.querySelectorAll('.modal') || []).forEach(($modal) => {
            closeModal($modal);
        });
    }

    // Add a click event on buttons to open a specific modal
    (document.querySelectorAll('.js-modal-trigger') || []).forEach(($trigger) => {
        const modal = $trigger.dataset.target;
        const $target = document.getElementById(modal);

        $trigger.addEventListener('click', () => {
            openModal($target);
        });
    });

    // Add a click event on various child elements to close the parent modal
    (document.querySelectorAll('.modal-background, .modal-close, .modal-card-head .delete, .modal-card-foot .button') || []).forEach(($close) => {
        const $target = $close.closest('.modal');

        $close.addEventListener('click', () => {
            closeModal($target);
        });
    });

    // Add a keyboard event to close all modals
    document.addEventListener('keydown', (event) => {
        if(event.key === "Escape") {
            closeAllModals();
        }
    });
});