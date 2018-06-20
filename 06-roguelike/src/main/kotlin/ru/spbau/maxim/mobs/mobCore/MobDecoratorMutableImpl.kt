package ru.spbau.maxim.mobs.mobCore

/**
 * Implements MobDecoratorMutable
 */
abstract class MobDecoratorMutableImpl: MobDecoratorImpl(), MobDecoratorMutable {
    private var mob: Mob? = null

    override fun getInnerMob(): Mob = mob!!

    override fun setMob(mob: Mob) {
        this.mob = mob
    }
}