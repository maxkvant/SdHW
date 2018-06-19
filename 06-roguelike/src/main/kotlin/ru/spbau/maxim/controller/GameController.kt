package ru.spbau.maxim.controller

import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.view.GameView
import ru.spbau.maxim.actions.Action
import ru.spbau.maxim.actions.HitAction

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
            val mobActions: List<Pair<MobWithEffects, Action>> = mobs.map { mob -> Pair(mob, mob.turn(model)) }

            val validActions = mobActions.filter { (mob, action) -> action.validate(mob, model) }

            validActions.forEach { (mob, action) ->
                when (action) {
                    is HitAction -> {
                        val deadBefore = action.victim.isDead()
                        action.executeIfValid(mob, model)
                        val deadAfter = action.victim.isDead()
                        if (!deadAfter && deadBefore) {
                            val victimArtifacts = action.victim.artifactStorage.getArtifacts().shuffled()
                            victimArtifacts.forEach { artifact ->
                                mob.artifactStorage.tryAddArtifact(artifact)
                            }
                        }
                    }
                    else -> action.executeIfValid(mob, model)
                }
            }
        }
    }
}
