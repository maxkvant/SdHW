package ru.spbau.maxim.mobs.actions

import ru.spbau.maxim.mobs.mobCore.Mob
import ru.spbau.maxim.mobs.mobCore.MobWithEffects
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position
import kotlin.math.max

/**
 * Hits sibling, decreases it's hp by max(0, attack - victim.defence)
 */
class HitSibling(private val causeOldPos: Position, override val victim: MobWithEffects): HitAction {
    private val victimOldPos = victim.getPosition()

    override fun validate(author: MobWithEffects, model: ModelReadOnly): Boolean {
        return causeOldPos.dist(victimOldPos) == 1
    }

    override fun execute(author: MobWithEffects, model: Model) {
        val hp = max(0, author.attack - victim.defence)
        (victim as Mob).decreaseHp(hp)
    }
}