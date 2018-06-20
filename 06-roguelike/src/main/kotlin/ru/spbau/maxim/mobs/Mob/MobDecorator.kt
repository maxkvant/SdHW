package ru.spbau.maxim.mobs.Mob

/**
 * Interface for abstract mob decorator
 */
interface MobDecorator: Mob {
    /**
     * return wrapped by current decorator Mob
     */
    fun getInnerMob(): Mob
}