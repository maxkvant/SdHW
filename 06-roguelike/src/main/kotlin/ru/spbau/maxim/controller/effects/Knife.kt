package ru.spbau.maxim.controller.effects

import ru.spbau.maxim.mobs.Mob.Mob
import ru.spbau.maxim.mobs.Mob.MobDecorator

class Knife: Effect {
    override fun apply(mob: Mob): Mob {
        return object: MobDecorator(mob) {
            override val attack = mob.attack + 5
        }
    }

    override val timeFinish: Int
        get() = Int.MAX_VALUE
}