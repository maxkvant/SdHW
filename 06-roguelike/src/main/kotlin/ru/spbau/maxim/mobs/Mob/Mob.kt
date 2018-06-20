package ru.spbau.maxim.mobs.Mob

import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.mobs.artifacts.ArtifactStorage
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position

interface Mob: MobReadOnly {
    fun turn(env: ModelReadOnly): Action

    fun decreaseHp(attack: Int)

    fun increaseHp(heal: Int)

    fun moveTo(pos: Position, model: Model)

    override val artifactStorage: ArtifactStorage
}