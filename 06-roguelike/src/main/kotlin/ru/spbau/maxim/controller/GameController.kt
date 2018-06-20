package ru.spbau.maxim.controller

import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.mobs.PlayerMob.*
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.view.GameView
import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.mobs.actions.HitAction
import ru.spbau.maxim.mobs.actions.Rest
import ru.spbau.maxim.mobs.effects.Frozen
import ru.spbau.maxim.view.GameView.*

class GameController(val model: Model, private val view: GameView) {
    init {
        view.draw(model)
        view.addInputListener(InputListener.of { input ->
            if (input.isKeyStroke()) {
                val keyStroke = input.asKeyStroke()
                val inputType = keyStroke.getInputType()

                val playerTurn: PlayerTurn? = when (inputType) {
                    InputType.ArrowRight -> PlayerTurn.RIGHT
                    InputType.ArrowUp    -> PlayerTurn.UP
                    InputType.ArrowLeft  -> PlayerTurn.LEFT
                    InputType.ArrowDown  -> PlayerTurn.DOWN
                    InputType.Enter      -> PlayerTurn.REST
                    else -> null
                }

                if (playerTurn != null) {
                    val player = model.getPlayer()
                    player.setPlayerTurn(playerTurn)

                    if (player.turn(model).validate(player, model)) {
                        turn()
                    }
                }
            }
        })
        model.getPlayer().addDecorator(Frozen(), 3, model)

    }

    private fun turn() {
        model.nextTurn {
            if (!finished()) {
                getMobs().forEach { mob ->
                    if (mob.isDead()) {
                        removeMob(mob)
                    }
                }

                val mobs = getMobs()
                val mobActions: List<Pair<MobWithEffects, Action>> = mobs.map { mob -> Pair(mob, mob.turn(this)) }

                val validActions = mobActions.filter { (mob, action) ->
                    action.validate(mob, this)
                }

                validActions.forEach { (mob, action) ->
                    when (action) {
                        is HitAction -> {
                            val deadBefore = action.victim.isDead()
                            action.executeIfValid(mob, this)
                            val deadAfter = action.victim.isDead()
                            if (!deadAfter && deadBefore) {
                                val victimArtifacts = action.victim.artifactStorage.getArtifacts().shuffled()
                                victimArtifacts.forEach { artifact ->
                                    mob.artifactStorage.tryAddArtifact(artifact)
                                }
                            }
                        }
                        else -> action.executeIfValid(mob, this)
                    }
                }
            }
        }
        view.draw(model)
    }
}
