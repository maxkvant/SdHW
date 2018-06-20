package ru.spbau.maxim.mobs

import ru.spbau.maxim.mobs.mobCore.Mob

/**
 * Interface for player mobCore
 * You can tell it, what action player have chosen
 */
interface PlayerMob: Mob {
    fun setPlayerTurn(playerTurn: PlayerTurn)

    enum class PlayerTurn {
        RIGHT,
        UP,
        LEFT,
        DOWN,
        REST
    }
}