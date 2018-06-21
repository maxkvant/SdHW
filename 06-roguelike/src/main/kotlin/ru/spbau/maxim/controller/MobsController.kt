package ru.spbau.maxim.controller

import mu.KotlinLogging
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import ru.spbau.maxim.mobs.mobCore.MobWithEffects
import ru.spbau.maxim.mobs.PlayerMob
import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.mobs.actions.HitAction
import ru.spbau.maxim.mobs.effects.Frozen
import ru.spbau.maxim.model.Model
import kotlin.math.log


/**
 * Reacts to player moves/attacks, executes other mobs actions on each turn
 * @see SubController
 */
class MobsController(private val model: Model): SubController {
    private val logger = KotlinLogging.logger { MobsController::class.java }

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

                logger.info { "player: pos ${player.getPosition()}, hp ${player.getHp()}" }

                if (player.turn(model).validate(player, model)) {
                    logger.info { "player [$player] turn $playerTurn is valid" }
                    turn()
                    return true
                } else {
                    logger.info { "player [$player] turn $playerTurn is invalid" }
                }
            }
        }
        return false
    }

    private fun turn() {
        val timeBefore = System.currentTimeMillis()
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
                            logger.debug { "mob [$mob] hits [${action.victim}] with action [$action]" }
                            val deadBefore = action.victim.isDead()
                            action.executeIfValid(mob, this)
                            val deadAfter = action.victim.isDead()

                            if (deadAfter && !deadBefore) {
                                logger.debug { "${action.victim} dead after [$mob] hit" }

                                val victimArtifacts = action.victim.artifactStorage.getArtifacts().shuffled()
                                victimArtifacts.forEach { artifact ->
                                    val added = mob.artifactStorage.tryAddArtifact(artifact)
                                    if (added) {
                                        logger.info { "added $artifact to mob [$mob]" }
                                    }
                                }
                            }
                        }
                        else -> {
                            logger.debug { "mob does ${action}" }
                            action.executeIfValid(mob, this)
                        }
                    }
                }
            }
        }
        val turnTimeMillis = System.currentTimeMillis() - timeBefore
        logger.info { "turn processing took ${turnTimeMillis}ms" }
    }
}