package ru.spbau.maxim.mobs.Mob

import ru.spbau.maxim.mobs.artifacts.ArtifactStorage
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position

/**
 * Implements decorator pattern for Mob
 */
abstract class MobDecoratorImpl: MobDecorator {
    override val attack: Int
            get() = getInnerMob().attack

    override val defence: Int
            get() = getInnerMob().defence

    override val artifactStorage: ArtifactStorage
        get() = getInnerMob().artifactStorage

    override fun turn(env: Model) = getInnerMob().turn(env)

    final override fun getHp(): Int = getInnerMob().getHp()

    override fun decreaseHp(attack: Int) = getInnerMob().decreaseHp(attack)

    override fun increaseHp(heal: Int) = getInnerMob().increaseHp(heal)

    final override fun moveTo(posTo: Position, model: Model) = getInnerMob().moveTo(posTo, model)

    final override fun getPosition(): Position = getInnerMob().getPosition()
}