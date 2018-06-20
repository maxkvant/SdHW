package ru.spbau.maxim.model.impl

import ru.spbau.maxim.mobs.Mob.MobReadOnly
import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.mobs.PlayerMobWithEffects
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.field.FieldReadOnly

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

    override fun time(): Int = turns

    override fun finished(): Boolean = getPlayer().isDead()

    override fun getMobs(): List<MobWithEffects> = mobStorage.getMobs()

    override fun getMob(pos: Position): MobWithEffects? = mobStorage.getMob(pos)

    override fun addMob(mobWithEffects: MobWithEffects) = mobStorage.addMob(mobWithEffects)

    override fun removeMob(mobWithEffects: MobWithEffects) = mobStorage.removeMob(mobWithEffects)

    override fun getPlayer(): PlayerMobWithEffects = curPlayer

    override fun updateMobPos(oldPos: Position) = mobStorage.updateMobPosition(oldPos)

    override fun getMobsReadOnly(): List<MobReadOnly> = getMobs()

    override fun getCell(position: Position): Cell = field.get(position)

    override fun getMobReadOnly(position: Position): MobReadOnly? = getMob(position)

    override fun getPlayerReadOnly(): MobReadOnly = getPlayer()
}