package ru.spbau.maxim.controller.effects

import ru.spbau.maxim.controller.actions.Rest
import ru.spbau.maxim.mobs.Mob.Mob
import ru.spbau.maxim.mobs.Mob.MobDecorator
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.controller.actions.Action

class Frozen(time: Int): Effect {
    override val timeFinish = time + 4
    override fun apply(mob: Mob): Mob {
        return object: MobDecorator(mob) {
            override fun turn(env: ModelReadOnly): Action = Rest()
        }
    }

}