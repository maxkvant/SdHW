package ru.spbau.maxim.controller.actions

import ru.spbau.maxim.mobs.Mob.Mob
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import kotlin.math.max

class HitSibling(private val cause: Mob, private val victim: Mob): Action {
    private val causeOldPos = cause.getPosition()
    private val victimOldPos = victim.getPosition()

    override fun validate(model: ModelReadOnly): Boolean {
        return causeOldPos.dist(victimOldPos) == 1
    }

    override fun execute(model: Model) {
        val hp = max(0, cause.attack - victim.defence)
        victim.decreaseHp(hp)
    }
}