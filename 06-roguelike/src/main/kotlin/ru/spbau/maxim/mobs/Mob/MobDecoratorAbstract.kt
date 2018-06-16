package ru.spbau.maxim.mobs.Mob

import ru.spbau.maxim.model.Artifacts.ArtifactStorage
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position

abstract class MobDecoratorAbstract: MobDecorator {
    override val attack: Int
            get() = getInnerMob().attack

    override val defence: Int
            get() = getInnerMob().defence

    override val type: MobType
        get() = getInnerMob().type

    override val artifactStorage: ArtifactStorage
        get() = getInnerMob().artifactStorage

    override fun getHp(): Int = getInnerMob().getHp()

    override fun turn(env: ModelReadOnly) = getInnerMob().turn(env)

    override fun decreaseHp(attack: Int) = getInnerMob().decreaseHp(attack)

    override fun increaseHp(heal: Int) = getInnerMob().increaseHp(heal)

    override fun isDead(): Boolean = getInnerMob().isDead()

    override fun moveTo(pos: Position, model: Model) = getInnerMob().moveTo(pos, model)

    override fun getPosition(): Position = getInnerMob().getPosition()

}