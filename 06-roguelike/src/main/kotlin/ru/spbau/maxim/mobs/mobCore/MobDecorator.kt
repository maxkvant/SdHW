package ru.spbau.maxim.mobs.mobCore

/**
 * Interface for abstract mob decorator
 */
interface MobDecorator: Mob {
    /**
     * return wrapped by current decorator mobCore
     */
    fun getInnerMob(): Mob
}