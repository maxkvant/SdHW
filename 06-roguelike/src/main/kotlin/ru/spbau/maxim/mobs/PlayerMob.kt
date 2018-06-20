package ru.spbau.maxim.mobs

import ru.spbau.maxim.mobs.Mob.Mob

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