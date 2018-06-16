package ru.spbau.maxim.mobs.Mob

interface MobDecorator: Mob {
    fun getInnerMob(): Mob
}