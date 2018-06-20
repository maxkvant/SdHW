package ru.spbau.maxim.controller

import ru.spbau.maxim.model.Model
import ru.spbau.maxim.view.GameView
import ru.spbau.maxim.view.GameView.*

class GameController(val model: Model, private val view: GameView) {
    private val playerMobListener = PlayerMobController(model)
    private val artifactStorageListener = ArtifactStorageController(model)

    init {
        view.draw(model)
        view.addInputListener(InputListener.of { input ->
            val repaint = playerMobListener.onAction(input)
            if (repaint) {
                view.draw(model)
            }
        })

        view.addInputListener(InputListener.of { input ->
            val repaint = artifactStorageListener.onAction(input)
            if (repaint) {
                view.draw(model)
            }
        })

    }


}
