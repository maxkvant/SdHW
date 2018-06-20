package ru.spbau.maxim.mobs.actions

import ru.spbau.maxim.mobs.Mob.Mob
import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position
import kotlin.math.max

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