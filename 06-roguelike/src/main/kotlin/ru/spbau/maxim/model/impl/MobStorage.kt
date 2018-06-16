package ru.spbau.maxim.model.impl

import ru.spbau.maxim.mobs.Mob.Mob
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.field.FieldReadOnly

class MobStorage(val field: FieldReadOnly<Cell>) {
    val posMob = mutableMapOf<Position, Mob>()
    val mobPos = mutableMapOf<Mob, Position>()

    fun addMob(mob: Mob) {
        val pos = mob.getPosition()
        require(posMob[pos] == null)
        updateMobPosition(mob)
    }

    fun updateMobPosition(mob: Mob) {
        val oldPos: Position? = mobPos[mob]
        val pos = mob.getPosition()
        if (oldPos != null) {
            posMob.remove(oldPos)
        }
        posMob[mob.getPosition()] = mob
        mobPos[mob] = pos
    }

    fun getMobs(): List<Mob> {
        return posMob.values.toList()
    }

    fun getMob(position: Position): Mob? {
        return posMob[position]
    }

    fun setMobs(mobs: List<Mob>) {
        posMob.clear()
        mobs.forEach { addMob(it) }
    }

    fun removeMob(mob: Mob) {
        val pos = mob.getPosition()
        require(posMob[mob.getPosition()] === mob)
        mobPos.remove(mob)
        posMob.remove(pos)
    }
}