package ru.spbau.maxim.mobs.actions

import ru.spbau.maxim.mobs.effects.Freezer
import ru.spbau.maxim.mobs.effects.Frozen
import ru.spbau.maxim.mobs.mobCore.MobWithEffects
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position

/**
 * Freezes victim if not Frozen, otherwise runs causeAction
 */
class FreezeSibling(
        private val causeOldPos: Position,
        override val victim: MobWithEffects,
        private val causeAction: Action
): HitAction {
    private val victimOldPos = victim.getPosition()

    override fun validate(author: MobWithEffects, model: ModelReadOnly): Boolean {
        return super.validate(author, model) && causeAction.validate(author, model) && causeOldPos.dist(victimOldPos) == 1
    }

    override fun execute(author: MobWithEffects, model: Model) {
        if (!victim.hasDecorator(Frozen::class)) {
            val effect = Frozen()
            victim.addDecorator(effect, effect.timeActive, model)
        } else {
            causeAction.execute(author, model)
        }
    }
}