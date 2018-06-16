package ru.spbau.maxim.controller.effects

import ru.spbau.maxim.mobs.Mob.Mob

interface Effect {
    fun apply(mob: Mob): Mob
    val timeFinish: Int
}