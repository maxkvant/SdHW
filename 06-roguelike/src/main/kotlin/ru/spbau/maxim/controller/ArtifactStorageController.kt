package ru.spbau.maxim.controller

import mu.KotlinLogging
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import ru.spbau.maxim.model.Model

/**
 * chooses/removes player's artifacts, on player actions
 * @see SubController
 */
class ArtifactStorageController(private val model: Model): SubController {
    private val logger = KotlinLogging.logger { ArtifactStorageController::class.java }

    override fun onAction(input: Input): Boolean {
        if (input.isKeyStroke()) {
            val keyStroke = input.asKeyStroke()
            val inputType = keyStroke.getInputType()
            val artifactStorage = model.getPlayer().artifactStorage


            when (inputType) {
                InputType.Delete -> {
                    val i = artifactStorage.getCurrentArtifactI()
                    logger.info { "trying to remove artifact [$i]" }
                    if (i != null && i < artifactStorage.capacity) {
                        val removed = artifactStorage.removeArtifact(i)
                        if (removed) {
                            logger.info { "removed artifact [$i]" }
                        }
                        return true
                    }
                }
                InputType.Character -> {
                    val c = keyStroke.getCharacter()
                    if (c.isDigit()) {
                        val i = c.toString().toInt()
                        if (0 < i && i <= artifactStorage.capacity) {
                            val chosen = artifactStorage.choseArtifact(i - 1)
                            if (chosen) {
                                logger.info { "chosen artifact [${i - 1}]" }
                            }
                        }
                    }
                    return true
                }
                else -> { }
            }
        }
        return false
    }
}