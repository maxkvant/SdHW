package ru.spbau.maxim.actions

import ru.spbau.maxim.actions.Action
import ru.spbau.maxim.model.Mob
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position
import kotlin.math.max

class HitSibling(private val causeOldPos: Position, override val victim: Mob): HitAction {
    private val victimOldPos = victim.getPosition()

    override fun validate(model: ModelReadOnly): Boolean {
        return causeOldPos.dist(victimOldPos) == 1
    }

    override fun execute(model: Model) {
        val hp = max(0, model.getMob(causeOldPos)!!.attack - victim.defence)
        victim.decreaseHp(hp)
    }
}