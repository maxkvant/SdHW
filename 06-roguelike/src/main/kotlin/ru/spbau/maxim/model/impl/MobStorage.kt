package ru.spbau.maxim.model.impl

import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.field.FieldReadOnly

class MobStorage(val field: FieldReadOnly<Cell>) {
    val posMob = mutableMapOf<Position, MobWithEffects>()
    val mobPos = mutableMapOf<MobWithEffects, Position>()

    fun addMob(mobWithEffects: MobWithEffects) {
        val pos = mobWithEffects.getPosition()
        require(posMob[pos] == null)

        posMob[mobWithEffects.getPosition()] = mobWithEffects
        mobPos[mobWithEffects] = pos
    }

    fun updateMobPosition(oldPos: Position) {
        val mobWithEffects: MobWithEffects? = posMob[oldPos]
        if (mobWithEffects != null) {
            val pos = mobWithEffects.getPosition()
            posMob.remove(oldPos)
            posMob[mobWithEffects.getPosition()] = mobWithEffects
            mobPos[mobWithEffects] = pos
        }
    }

    fun getMobs(): List<MobWithEffects> {
        return posMob.values.toList()
    }

    fun getMob(position: Position): MobWithEffects? {
        return posMob[position]
    }

    fun removeMob(mobWithEffects: MobWithEffects) {
        val pos = mobWithEffects.getPosition()
        require(posMob[mobWithEffects.getPosition()] === mobWithEffects)
        mobPos.remove(mobWithEffects)
        posMob.remove(pos)
    }
}