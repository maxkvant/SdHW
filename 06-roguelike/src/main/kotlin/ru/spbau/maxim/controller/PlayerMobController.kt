package ru.spbau.maxim.controller

import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.mobs.PlayerMob
import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.mobs.actions.HitAction
import ru.spbau.maxim.model.Model

class PlayerMobController(private val model: Model): SubController {
    override fun onAction(input: Input): Boolean {
        if (input.isKeyStroke()) {
            val keyStroke = input.asKeyStroke()
            val inputType = keyStroke.getInputType()

            val playerTurn: PlayerMob.PlayerTurn? = when (inputType) {
                InputType.ArrowRight -> PlayerMob.PlayerTurn.RIGHT
                InputType.ArrowUp    -> PlayerMob.PlayerTurn.UP
                InputType.ArrowLeft  -> PlayerMob.PlayerTurn.LEFT
                InputType.ArrowDown  -> PlayerMob.PlayerTurn.DOWN
                InputType.Enter      -> PlayerMob.PlayerTurn.REST
                else -> null
            }

            if (playerTurn != null) {
                val player = model.getPlayer()
                player.setPlayerTurn(playerTurn)

                if (player.turn(model).validate(player, model)) {
                    turn()
                    return true
                }
            }
        }
        return false
    }

    private fun turn() {
        model.nextTurn {
            getPlayer().increaseHp(1)
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
                            if (deadAfter && !deadBefore) {
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
    }
}