package ru.spbau.maxim.model.impl

import ru.spbau.maxim.mobs.mobCore.MobReadOnly
import ru.spbau.maxim.mobs.mobCore.MobWithEffects
import ru.spbau.maxim.mobs.PlayerMobWithEffects
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.field.FieldReadOnly

/**
 * implements Model
 */
class ModelImpl(private val field: FieldReadOnly<Cell>, private val curPlayer: PlayerMobWithEffects, enemies: List<MobWithEffects>): Model {
    private val mobStorage = MobStorage(field)
    private var turns = 0

    init {
        enemies.forEach { mobStorage.addMob(it) }
        mobStorage.addMob(curPlayer)
    }

    override fun getField(): FieldReadOnly<Cell> = field

    override fun nextTurn(f: Model.() -> Unit) {
        if (!finished()) {
            f()
            getMobs().forEach { updateMobPos(it.getPosition()) }
            turns += 1
        }
    }

    override fun turns(): Int = turns

    override fun finished(): Boolean = getPlayer().isDead()

    override fun getMobs(): List<MobWithEffects> = mobStorage.getMobs()

    override fun getMob(pos: Position): MobWithEffects? = mobStorage.getMob(pos)

    override fun addMob(mob: MobWithEffects) = mobStorage.addMob(mob)

    override fun removeMob(mob: MobWithEffects) = mobStorage.removeMob(mob)

    override fun getPlayer(): PlayerMobWithEffects = curPlayer

    override fun updateMobPos(oldPos: Position) = mobStorage.updateMobPosition(oldPos)

    override fun getMobsReadOnly(): List<MobReadOnly> = getMobs()

    override fun getCell(position: Position): Cell = field[position]

    override fun getMobReadOnly(position: Position): MobReadOnly? = getMob(position)

    override fun getPlayerReadOnly(): MobReadOnly = getPlayer()
}