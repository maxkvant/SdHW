package ru.spbau.maxim.mobs.mobCore

/**
 * Implements MobDecoratorMutable
 */
abstract class MobDecoratorImpl: MobDecoratorAbstract(), MobDecoratorMutable {
    private var mob: Mob? = null

    override fun getInnerMob(): Mob = mob!!

    override fun setMob(mob: Mob) {
        this.mob = mob
    }
}