package ru.spbau.maxim.mobs.mobCore

import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.mobs.artifacts.ArtifactStorage
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.Position

/**
 * Interface for mobCore
 */
interface Mob: MobReadOnly {
    /**
     * increases mobCore's attack by attack
     */
    fun decreaseHp(attack: Int)

    /**
     * increases mobCore's hp by heal
     */
    fun increaseHp(heal: Int)

    /**
     * Moves mob to posTo
     */
    fun moveTo(posTo: Position, model: Model)

    /**
     * returns mobCore's action: what mobCore wants to do on that turn
     */
    fun turn(env: Model): Action

    /**
     * Stores mobCore's artifacts
     */
    override val artifactStorage: ArtifactStorage
}