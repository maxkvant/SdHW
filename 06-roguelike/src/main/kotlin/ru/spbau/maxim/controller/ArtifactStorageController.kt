package ru.spbau.maxim.controller

import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.mobs.PlayerMob
import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.mobs.actions.HitAction
import ru.spbau.maxim.model.Model

class ArtifactStorageController(private val model: Model): SubController {
    override fun onAction(input: Input): Boolean {
        if (input.isKeyStroke()) {
            val keyStroke = input.asKeyStroke()
            val inputType = keyStroke.getInputType()
            val artifactStorage = model.getPlayer().artifactStorage

            when (inputType) {
                InputType.Delete -> {
                    val i = artifactStorage.getCurrentArtifactI()
                    if (i != null && i < artifactStorage.capacity) {
                        artifactStorage.removeArtifact(i)
                        return true
                    }
                }
                InputType.Character -> {
                    val c = keyStroke.getCharacter()
                    if (c.isDigit()) {
                        val i = c.toString().toInt()
                        println(artifactStorage.capacity)
                        if (0 < i && i <= artifactStorage.capacity) {
                            println(i)
                            artifactStorage.choseArtifact(i - 1)
                        }
                        println(artifactStorage.getCurrentArtifactI())
                        println()
                    }
                    return true
                }
                else -> { }
            }
        }
        return false
    }
}