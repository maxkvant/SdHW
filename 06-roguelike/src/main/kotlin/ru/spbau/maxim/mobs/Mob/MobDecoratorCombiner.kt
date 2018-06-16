package ru.spbau.maxim.mobs.Mob

import ru.spbau.maxim.model.ModelReadOnly

class MobDecoratorCombiner(private val mob: Mob): MobDecoratorAbstract(), MobWithEffects {
    private val decorators: MutableList<Node> = mutableListOf()
    private var curMob = mob

    override fun getInnerMob(): Mob {
        return curMob
    }

    override fun addDecorator(decorator: MobDecoratorMutable, timeToLive: Int?, model: ModelReadOnly) {
        decorators.add(Node(decorator, timeToLive?.plus(model.time())))
    }

    override fun onNewTurn(model: ModelReadOnly) {
        decorators.removeIf { it.timeToRemove != null && it.timeToRemove < model.time() }
        var newMob = mob
        decorators.forEach { (decorator, _) ->
            decorator.setMob(newMob)
            newMob = decorator
        }
        curMob = newMob
    }

    data class Node(val mobDecoratorAbstract: MobDecoratorMutable, val timeToRemove: Int?)
}