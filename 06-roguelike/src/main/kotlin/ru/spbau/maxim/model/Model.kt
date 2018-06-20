package ru.spbau.maxim.model

import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.mobs.PlayerMobWithEffects

/**
 * Interface for Model
 */
interface Model: ModelReadOnly {
    /**
     * returns all mobs that are in model
     */
    fun getMobs(): List<MobWithEffects>

    /**
     * returns mobWithEffects if there is mob at pos,
     * otherwise returns null
     */
    fun getMob(pos: Position): MobWithEffects?

    /**
     * add mob into model
     */
    fun addMob(mob: MobWithEffects)

    /**
     * removes mob from model
     */
    fun removeMob(mob: MobWithEffects)

    /**
     * returns player mob
     * player controls it's actions
     */
    fun getPlayer(): PlayerMobWithEffects

    /**
     * updates pos of mob, which is cashed at oldPos
     */
    fun updateMobPos(oldPos: Position)

    /**
     * does f on during next turn
     */
    fun nextTurn(f: Model.() -> Unit)
}

