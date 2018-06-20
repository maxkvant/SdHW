package ru.spbau.maxim.mobs.Mob
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.Position
import kotlin.math.max
import kotlin.math.min

/**
 * Abstract class for Mob,
 * Implements it's hp ad position changes
 */
abstract class MobAbstract(val maxHp: Int, private var pos: Position): Mob {
    private var hp: Int = maxHp

    override fun getHp() = hp

    override fun decreaseHp(attack: Int) {
        hp -= attack
        normalizeHp()
    }

    override fun increaseHp(heal: Int) {
        hp += heal
        normalizeHp()
    }

    override fun moveTo(posTo: Position, model: Model) {
        val oldPos = this.pos
        this.pos = posTo
        model.updateMobPos(oldPos)
    }

    override fun getPosition(): Position {
        return pos
    }

    private fun normalizeHp() {
        hp = max(0, min(hp, maxHp))
    }
}