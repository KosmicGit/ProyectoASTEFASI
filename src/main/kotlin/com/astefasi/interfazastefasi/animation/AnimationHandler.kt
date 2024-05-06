package com.astefasi.interfazastefasi.animation

import com.astefasi.interfazastefasi.util.Ficheros
import javafx.animation.FadeTransition
import javafx.animation.PauseTransition
import javafx.scene.Node
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.util.Duration
import java.net.URI

class AnimationHandler {

    companion object {
        fun fadeAnimation(componentOut : Node, componentIn : Node) {
            val fadeOut = FadeTransition(Duration.seconds(1.0), componentOut)
            fadeOut.fromValue = 1.0
            fadeOut.toValue = 0.0

            fadeOut.setOnFinished {
                componentOut.isVisible = false
                componentOut.isDisable = true

                componentIn.isVisible = true
                val fadeIn = FadeTransition(Duration.seconds(1.0), componentIn)
                fadeIn.fromValue = 0.0
                fadeIn.toValue = 1.0

                fadeIn.setOnFinished {
                    componentIn.isDisable = false
                }

                fadeIn.play()
            }
            fadeOut.play()
        }

        fun imageTransition(imageView1 : ImageView, imageView2 : ImageView) : FadeTransition {
            val images = arrayOf(
                "images/terapia1.png",
                "images/terapia2.png",
                "images/terapia3.png",
                "images/terapia4.png"
            )

            val fade = FadeTransition(Duration.seconds(2.0), imageView2)
            fade.fromValue = 1.0
            fade.toValue = 0.0

            var fadeIn = false
            var image = 2

            fade.setOnFinished {
                if (fadeIn) {
                    imageView1.image = Image(javaClass.getResourceAsStream("${Ficheros.RESOURCES_PATH}${images[image++]}"))
                    fade.fromValue = 1.0
                    fade.toValue = 0.0
                }else {
                    imageView2.image = Image(javaClass.getResourceAsStream("${Ficheros.RESOURCES_PATH}${images[image++]}"))
                    fade.fromValue = 0.0
                    fade.toValue = 1.0
                }

                if (image >= images.size) image = 0
                fadeIn = !fadeIn

                val pause = PauseTransition(Duration.seconds(3.0))
                pause.setOnFinished {
                    fade.playFromStart()
                }

                pause.play()
            }

            fade.play()
            return fade
        }
    }
}