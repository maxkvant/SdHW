package ru.spbau.maxim.mobs.Mob

import ru.spbau.maxim.mobs.artifacts.ArtifactsStorageReadOnly
import ru.spbau.maxim.model.Position

/**
 * Interface for read only access to Mob
 * methods shouldn't modify mob
 */
interface MobReadOnly {
    val attack: Int

    val defence: Int

    fun getHp(): Int

    fun isDead(): Boolean = getHp() == 0

    /**
     * position mof mog
     */
    fun getPosition(): Position

    val artifactStorage: ArtifactsStorageReadOnly
}