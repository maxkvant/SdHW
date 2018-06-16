package ru.spbau.maxim.controller.effects

import ru.spbau.maxim.mobs.Mob.Mob

class FirstAid(override val timeFinish: Int) : Effect {
    override fun apply(mob: Mob): Mob {
        mob.increaseHp(2)
        return mob
    }

}