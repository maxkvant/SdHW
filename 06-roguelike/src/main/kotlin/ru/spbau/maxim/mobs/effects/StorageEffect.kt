package ru.spbau.maxim.mobs.effects

import ru.spbau.maxim.mobs.mobCore.Mob
import ru.spbau.maxim.mobs.artifacts.Artifact

/**
 * Effect of having some artifacts
 */
class StorageEffect: EffectAbstract() {
    override fun getInnerMob(): Mob {
        val prevMob: Mob = super.getInnerMob()
        val artifact: Artifact? = prevMob.artifactStorage.getCurrentArtifact()
        val effect: Effect? = artifact?.effect?.invoke()
        effect?.setMob(prevMob)
        return effect ?: prevMob
    }
}