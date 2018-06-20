package ru.spbau.maxim.controller

import org.codetome.zircon.api.input.InputType
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.view.GameView
import ru.spbau.maxim.view.GameView.*

/**
 * Runs game in view
 * when player dies new model generates by modelGenerator
 */
class GameController(modelGenerator: () -> Model, private val view: GameView) {
    private var subControllers = SubControllers(modelGenerator())

    private val model: Model
        get() = subControllers.model

    init {
        view.draw(model)
        view.addInputListener(InputListener.of { input ->
            val repaint = subControllers.mobController.onAction(input)
            if (repaint) {
                view.draw(model)
                if (model.finished()) {
                    subControllers = SubControllers(modelGenerator())
                    view.draw(model)
                }
            }
        })

        view.addInputListener(InputListener.of { input ->
            val repaint = subControllers.artifactController.onAction(input)
            if (repaint) {
                view.draw(model)
            }
        })

        view.addInputListener(InputListener.of { input ->
            if (input.inputTypeIs(InputType.Character) && input.asKeyStroke().getCharacter() == 'q') {
                view.close()
            }
        })

    }

    private class SubControllers(val model: Model) {
        val mobController = MobsController(model)
        val artifactController = ArtifactStorageController(model)
    }
}
