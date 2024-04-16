package es.cifpvirgen.Pages.Dashboard

import es.cifpvirgen.Pages.checkUser
import kweb.components.Component

fun Component.dashPage() {
    if (!checkUser()) {

    }
}