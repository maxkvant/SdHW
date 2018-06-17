package ru.spbau.maxim.actions

import ru.spbau.maxim.model.Mob

interface HitAction: Action {
    val victim: Mob
}