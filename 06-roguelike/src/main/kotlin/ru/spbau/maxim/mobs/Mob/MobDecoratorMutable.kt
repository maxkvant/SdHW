package ru.spbau.maxim.mobs.Mob

/**
 * Interface for MobDecorator, where you can change inner mob
 */
interface MobDecoratorMutable: MobDecorator {
    /**
     * change innerMob to mob
     */
    fun setMob(mob: Mob)
}