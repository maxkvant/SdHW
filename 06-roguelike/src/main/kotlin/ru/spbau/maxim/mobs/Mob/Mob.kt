package ru.spbau.maxim.mobs.Mob

import ru.spbau.maxim.actions.Action
import ru.spbau.maxim.model.Artifacts.ArtifactStorage
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position

interface Mob: MobReadOnly {
    fun turn(env: ModelReadOnly): Action

    fun decreaseHp(attack: Int)

    fun increaseHp(heal: Int)

    fun moveTo(pos: Position, model: Model)

    val artifactStorage: ArtifactStorage
}