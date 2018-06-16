package ru.spbau.maxim.model.impl

import ru.spbau.maxim.controller.effects.Effect
import ru.spbau.maxim.mobs.Mob.Mob
import ru.spbau.maxim.model.Model

class EffectStorage {
    private val mobEffects = mutableMapOf<Mob, List<Effect>>()

    fun addEffect(mob: Mob, effect: Effect) {
        val effects = (mobEffects[mob] ?: listOf()).toMutableList()
        effects.add(effect)
        mobEffects[mob] = effects.toList()
    }

    fun getEffects(mob: Mob, model: Model): List<Effect> {
        removeOldEffects(mob, model)
        return mobEffects[mob] ?: listOf()
    }

    fun setEffects(mob: Mob, effects: List<Effect>) {
        mobEffects[mob] = effects
    }


    fun removeEffect(mob: Mob, effect: Effect) {
        val effects = mobEffects[mob]
        if (effects != null) {
            mobEffects[mob] = effects.filter { it != effect }
        }
    }

    fun applyEffects(mob: Mob, model: Model): Mob {
        val effects = getEffects(mob, model)
        var mobCur: Mob = mob
        effects.forEach { effect ->
            mobCur = effect.apply(mobCur)
        }
        return mobCur
    }

    fun cleanup(model: Model) {
        mobEffects.keys.forEach { mob ->
            removeOldEffects(mob, model)
        }
    }

    private fun removeOldEffects(mob: Mob, model: Model) {
        val effects = mobEffects[mob]
        if (effects != null) {
            mobEffects[mob] = effects.filter { it.timeFinish >= model.time() }
        }
    }
}