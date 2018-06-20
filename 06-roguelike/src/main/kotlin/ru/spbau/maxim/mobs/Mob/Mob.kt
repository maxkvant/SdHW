package ru.spbau.maxim.mobs.Mob

import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.mobs.artifacts.ArtifactStorage
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position

/**
 * Interface for Mob
 */
interface Mob: MobReadOnly {
    /**
     * increases Mob's attack by attack
     */
    fun decreaseHp(attack: Int)

    /**
     * increases Mob's hp by heal
     */
    fun increaseHp(heal: Int)

    /**
     * Moves mob to posTo
     */
    fun moveTo(posTo: Position, model: Model)

    /**
     * returns Mob's action: what Mob wants to do on that turn
     */
    fun turn(env: Model): Action

    /**
     * Stores Mob's artifacts
     */
    override val artifactStorage: ArtifactStorage
}