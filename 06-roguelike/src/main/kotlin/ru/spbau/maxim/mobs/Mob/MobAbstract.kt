package ru.spbau.maxim.mobs.Mob
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.Position
import kotlin.math.max
import kotlin.math.min

abstract class MobAbstract(val maxHp: Int, position: Position): Mob {
    private var hp: Int = maxHp
    private var pos: Position = position

    override fun getHp() = hp

    override fun decreaseHp(attack: Int) {
        hp -= attack
        normalizeHp()
    }

    override fun increaseHp(heal: Int) {
        hp += heal
        normalizeHp()
    }

    override fun isDead(): Boolean {
        return hp == 0
    }

    override fun moveTo(pos: Position, model: Model) {
        this.pos = pos
        model.updateMobPos(this)
    }

    override fun getPosition(): Position {
        return pos
    }

    private fun normalizeHp() {
        hp = max(0, min(hp, maxHp))
    }
}