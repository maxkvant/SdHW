package ru.spbau.maxim.model.impl

import ru.spbau.maxim.controller.effects.Effect
import ru.spbau.maxim.mobs.Mob.Mob
import ru.spbau.maxim.mobs.Mob.MobReadOnly
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.field.FieldReadOnly

class ModelImpl(val field: FieldReadOnly<Cell>, private val curPlayer: Mob, private val enemies: List<Mob>): Model {
    private val mobStorage = MobStorage(field)
    private val effectStorage = EffectStorage()
    private var turns = 0

    init {
        mobStorage.addMob(curPlayer)
    }

    override fun nextTurn() {
        turns += 1
        effectStorage.cleanup(this)
    }

    override fun applyEffects(mob: Mob) = effectStorage.applyEffects(mob, this)

    override fun time(): Int = turns

    override fun finished(): Boolean = getPlayer().isDead()

    override fun getMobs(): List<Mob> = mobStorage.getMobs()

    override fun getMob(pos: Position): Mob? = mobStorage.getMob(pos)

    override fun addMob(mob: Mob) = mobStorage.addMob(mob)

    override fun getPlayer(): Mob = curPlayer

    override fun updateMobPos(mob: Mob) = mobStorage.updateMobPosition(mob)

    override fun getMobsReadOnly(): List<MobReadOnly> = getMobs()

    override fun getCell(position: Position): Cell = field.get(position)

    override fun getEffects(mob: Mob): List<Effect> = effectStorage.getEffects(mob, this)

    override fun addEffect(mob: Mob, effect: Effect) = effectStorage.addEffect(mob, effect)

    override fun setEffects(mob: Mob, effects: List<Effect>) = effectStorage.setEffects(mob, effects)

    override fun getMobReadOnly(position: Position): MobReadOnly? = getMob(position)

    override fun getPlayerReadOnly(): MobReadOnly = getPlayer()
}