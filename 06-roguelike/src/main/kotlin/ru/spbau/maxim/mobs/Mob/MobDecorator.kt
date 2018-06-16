package ru.spbau.maxim.mobs.Mob

import ru.spbau.maxim.model.Artifacts.ArtifactStorage
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position

open class MobDecorator(val mob: Mob): Mob {
    override val attack: Int
        get() = mob.attack
    override val defence: Int
        get() = mob.defence
    override val type: MobType
        get() = mob.type

    override fun getHp(): Int = mob.getHp()

    override fun turn(env: ModelReadOnly) = mob.turn(env)

    override fun decreaseHp(attack: Int) = mob.decreaseHp(attack)

    override fun increaseHp(heal: Int) = mob.increaseHp(heal)

    override fun isDead(): Boolean = mob.isDead()

    override fun moveTo(pos: Position, model: Model) = mob.moveTo(pos, model)

    override fun getPosition(): Position = mob.getPosition()

    override fun getArtifactsStorage(): ArtifactStorage = mob.getArtifactsStorage()
}

fun mobNoDecorators(mob: Mob): Mob = when (mob) {
    is MobDecorator -> mobNoDecorators(mob.mob)
    else -> mob
}