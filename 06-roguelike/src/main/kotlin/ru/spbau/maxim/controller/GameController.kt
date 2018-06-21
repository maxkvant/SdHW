package ru.spbau.maxim.controller

import mu.KotlinLogging
import org.codetome.zircon.api.input.InputType
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.view.GameView
import ru.spbau.maxim.view.GameView.*

/**
 * Runs game in view
 * when player dies new model generates by modelGenerator
 */
class GameController(modelGenerator: () -> Model, private val view: GameView) {
    private val logger = KotlinLogging.logger { GameController::class.java }

    private var subControllers = SubControllers(modelGenerator())

    private val model: Model
        get() = subControllers.model

    init {
        logger.info { "GameController is started" }

        view.draw(model)
        view.addInputListener(InputListener.of { input ->
            val repaint = subControllers.mobController.onAction(input)
            if (repaint) {
                view.draw(model)
                if (model.finished()) {
                    subControllers = SubControllers(modelGenerator())
                    logger.info { "game finished | restarting" }
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
                logger.info { "quit" }
                view.close()
            }
        })

    }

    private class SubControllers(val model: Model) {
        val mobController = MobsController(model)
        val artifactController = ArtifactStorageController(model)
    }
}
