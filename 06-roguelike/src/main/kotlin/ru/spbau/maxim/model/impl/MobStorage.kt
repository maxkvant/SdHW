package ru.spbau.maxim.model.impl

import ru.spbau.maxim.model.Mob
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.field.FieldReadOnly

class MobStorage(val field: FieldReadOnly<Cell>) {
    val posMob = mutableMapOf<Position, Mob>()
    val mobPos = mutableMapOf<Mob, Position>()

    fun addMob(mob: Mob) {
        val pos = mob.getPosition()
        require(posMob[pos] == null)

        posMob[mob.getPosition()] = mob
        mobPos[mob] = pos
    }

    fun updateMobPosition(oldPos: Position) {
        val mob: Mob = posMob[oldPos]!!
        val pos = mob.getPosition()

        posMob.remove(oldPos)
        posMob[mob.getPosition()] = mob
        mobPos[mob] = pos
    }

    fun getMobs(): List<Mob> {
        return posMob.values.toList()
    }

    fun getMob(position: Position): Mob? {
        return posMob[position]
    }

    fun removeMob(mob: Mob) {
        val pos = mob.getPosition()
        require(posMob[mob.getPosition()] === mob)
        mobPos.remove(mob)
        posMob.remove(pos)
    }
}