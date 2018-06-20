package ru.spbau.maxim.mobs.Mob

import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.mobs.artifacts.ArtifactStorage
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position

interface Mob: MobReadOnly {
    fun decreaseHp(attack: Int)

    fun increaseHp(heal: Int)

    fun moveTo(pos: Position, model: Model)

    fun turn(env: Model): Action

    override val artifactStorage: ArtifactStorage
}