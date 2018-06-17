package ru.spbau.maxim.effects

import ru.spbau.maxim.mobs.Mob.Effect
import ru.spbau.maxim.mobs.Mob.EffectAbstract
import ru.spbau.maxim.mobs.Mob.Mob
import ru.spbau.maxim.model.Artifacts.Artifact

class StorageEffect: EffectAbstract() {
    override fun getInnerMob(): Mob {
        val prevMob: Mob = super.getInnerMob()
        val artifact: Artifact? = prevMob.artifactStorage.getCurrentArtifact()
        val effect: Effect? = artifact?.effect?.invoke()
        effect?.setMob(prevMob)
        return effect ?: prevMob
    }
}