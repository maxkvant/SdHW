package ru.spbau.maxim.mobs

import ru.spbau.maxim.controller.actions.Rest
import ru.spbau.maxim.mobs.Mob.MobAbstract
import ru.spbau.maxim.mobs.Mob.MobType
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.controller.actions.Action
import ru.spbau.maxim.model.Artifacts.ArtifactStorage
import ru.spbau.maxim.model.Artifacts.EmptyArtifactStorage

class SleepingMob(position: Position) : MobAbstract(maxHp = 40, position = position) {
    private val artifactStorage = EmptyArtifactStorage()

    override val type: MobType
        get() = MobType.ENEMY
    override val defence: Int
        get() = 0
    override val attack: Int
        get() = 4

    override fun turn(env: ModelReadOnly): Action = Rest()

    override fun getArtifactsStorage(): ArtifactStorage {
        return artifactStorage
    }
}