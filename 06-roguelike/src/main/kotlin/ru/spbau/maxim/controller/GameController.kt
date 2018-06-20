package ru.spbau.maxim.controller

import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.mobs.PlayerMob.*
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.view.GameView
import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.mobs.actions.HitAction
import ru.spbau.maxim.mobs.actions.Rest

class GameController(val model: Model, private val view: GameView) {

    fun runGame(beforeTurn: () -> Unit) {
        while (!model.finished()) {
            beforeTurn()
            println("hp: ${model.getPlayer().getHp()}")
            val c = readLine()
            val playerTurn = when (c) {
                ">" -> PlayerTurn.RIGHT
                "^" -> PlayerTurn.UP
                "<" -> PlayerTurn.LEFT
                "v" -> PlayerTurn.DOWN
                else -> PlayerTurn.REST
            }
            println(playerTurn)
            model.getPlayer().setPlayerTurn(playerTurn)
            turn()
        }
    }

    fun turn() {
        model.nextTurn {
            if (!finished()) {
                getMobs().forEach { mob ->
                    if (mob.isDead()) {
                        removeMob(mob)
                    }
                }

                val mobs = getMobs()
                val mobActions: List<Pair<MobWithEffects, Action>> = mobs.map { mob -> Pair(mob, mob.turn(this)) }
                println(mobActions)

                val validActions = mobActions.filter { (mob, action)
                    -> action.validate(mob, this) && action !is Rest
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
    }
}
