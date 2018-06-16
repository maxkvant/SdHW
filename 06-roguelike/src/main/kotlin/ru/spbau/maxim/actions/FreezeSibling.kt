package ru.spbau.maxim.actions

import ru.spbau.maxim.effects.Frozen
import ru.spbau.maxim.model.Mob
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import java.util.*

class FreezeSibling(cause: Mob, private val victim: Mob): Action {
    private val causeOldPos = cause.getPosition()
    private val victimOldPos = victim.getPosition()

    override fun validate(model: ModelReadOnly): Boolean {
        return causeOldPos.dist(victimOldPos) == 1
    }

    override fun execute(model: Model) {
        if (Random().nextBoolean()) {
            val effect = Frozen()
            victim.addDecorator(effect, effect.timeToLive, model)
        }
    }
}