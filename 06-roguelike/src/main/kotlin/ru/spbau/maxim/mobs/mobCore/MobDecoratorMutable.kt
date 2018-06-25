package ru.spbau.maxim.mobs.mobCore

/**
 * Interface for MobDecorator, where you can change inner mob
 */
interface MobDecoratorMutable: Mob {
    /**
     * change innerMob to mob
     */
    fun setMob(mob: Mob)
}