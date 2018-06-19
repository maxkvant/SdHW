package ru.spbau.maxim.actions

import ru.spbau.maxim.effects.Freezer
import ru.spbau.maxim.effects.Frozen
import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.model.Mob
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position
import java.util.*

class FreezeSibling(val causeOldPos: Position, override val victim: Mob): HitAction {
    private val victimOldPos = victim.getPosition()

    override fun validate(author: MobWithEffects, model: ModelReadOnly): Boolean {
        return super.validate(author, model) && causeOldPos.dist(victimOldPos) == 1
    }

    override fun execute(author: MobWithEffects, model: Model) {
        if (!victim.hasSuch(Freezer::class) && Random().nextBoolean()) {
            val effect = Frozen()
            victim.addDecorator(effect, effect.timeToLive, model)
        }
    }
}