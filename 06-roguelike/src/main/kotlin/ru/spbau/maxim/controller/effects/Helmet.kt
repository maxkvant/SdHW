package ru.spbau.maxim.controller.effects

import ru.spbau.maxim.mobs.Mob.Mob
import ru.spbau.maxim.mobs.Mob.MobDecorator

class Helmet: Effect {
    override fun apply(mob: Mob): Mob {
        return object: MobDecorator(mob) {
            override val defence = mob.defence + 5
        }
    }

    override val timeFinish: Int
        get() = Int.MAX_VALUE
}