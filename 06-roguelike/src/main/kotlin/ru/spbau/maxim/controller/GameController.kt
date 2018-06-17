package ru.spbau.maxim.controller

import com.sun.xml.internal.ws.api.policy.ModelGenerator
import ru.spbau.maxim.mobs.Mob.Mob
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.view.GameView
import ru.spbau.maxim.actions.Action

class GameController(modelGenerator: () -> Model, private val view: GameView) {
    var model: Model = modelGenerator.invoke()


    fun runGame() {
        while (model.finished()) {
            turn()
        }
    }

    fun turn() {
        if (!model.finished()) {
            model.getMobs().forEach { mob ->
                if (mob.isDead()) {
                    model.removeMob(mob)
                }
            }

            val mobs = model.getMobs()
            val actions = mobs.map { it.turn(model) }
            val validActions = actions.filter { it.validate(model) }
            validActions.forEach { it.executeIfValid(model) }
        }
    }
}
