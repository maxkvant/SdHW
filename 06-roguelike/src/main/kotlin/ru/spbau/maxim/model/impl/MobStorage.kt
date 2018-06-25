package ru.spbau.maxim.model.impl

import ru.spbau.maxim.mobs.mobCore.MobWithEffects
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.field.FieldReadOnly

/**
 * Stores mobs
 */
class MobStorage(val field: FieldReadOnly<Cell>) {
    private val posMob = mutableMapOf<Position, MobWithEffects>()
    private val mobPos = mutableMapOf<MobWithEffects, Position>()

    /**
     * adds mob to it's position
     * if position is already occupied or throws exception
     */
    fun addMob(mob: MobWithEffects) {
        val pos = mob.getPosition()
        require(posMob[pos] == null)

        posMob[mob.getPosition()] = mob
        mobPos[mob] = pos
    }

    /**
     * updates mob position, that is cashed in oldPos to new one
     */
    fun updateMobPosition(oldPos: Position) {
        val mobWithEffects: MobWithEffects? = posMob[oldPos]
        if (mobWithEffects != null) {
            val pos = mobWithEffects.getPosition()
            posMob.remove(oldPos)
            posMob[mobWithEffects.getPosition()] = mobWithEffects
            mobPos[mobWithEffects] = pos
        }
    }

    /**
     * returns all mobs stored here
     */
    fun getMobs(): List<MobWithEffects> {
        return posMob.values.toList()
    }

    /**
     * returns MobWithEffect if there is mob at position
     * otherwise returns null
     */
    fun getMob(position: Position): MobWithEffects? {
        return posMob[position]
    }

    /**
     * removes mob from storage
     */
    fun removeMob(mob: MobWithEffects) {
        val pos = mob.getPosition()
        require(posMob[mob.getPosition()] === mob)
        mobPos.remove(mob)
        posMob.remove(pos)
    }
}