package ru.spbau.maxim.mobs

import ru.spbau.maxim.mobs.Mob.Mob

/**
 * Interface for player Mob
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