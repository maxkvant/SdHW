package ru.spbau.maxim.model

import ru.spbau.maxim.mobs.mobCore.MobReadOnly
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.field.FieldReadOnly

/**
 * Interface for readonly part of Model
 */
interface ModelReadOnly {
    /**
     * returns Field
     */
    fun getField(): FieldReadOnly<Cell>

    /**
     * returns cell of field at position
     */
    fun getCell(position: Position): Cell

    /**
     * returns read only version of player mob
     */
    fun getPlayerReadOnly(): MobReadOnly

    /**
     * returns read only version of mob at position if there is mob at position,
     * otherwise returns null
     */
    fun getMobReadOnly(position: Position): MobReadOnly?

    /**
     * returns how many turns were done
     */
    fun turns(): Int

    /**
     * checks is game finished or not
     */
    fun finished(): Boolean

    /**
     * returns read only versions of mobs that are in model
     */
    fun getMobsReadOnly(): List<MobReadOnly>
}