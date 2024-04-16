package es.cifpvirgen

import kweb.*

fun main() {
    Kweb(port = 16097) {
        doc.head {
            title().text("\uD83C\uDF08  Terapia Web. \uD83C\uDF24\uFE0F")
        }
        doc.body {
            route {
                // URL "/"
                path("/") {

                }

                // URL "/login"
                path("/login") {

                }

                // URL "/dashboard"
                path("/dashboard") {

                }

                notFound {
                    h1().text("404 Not Found")
                }
            }
        }
    }
}