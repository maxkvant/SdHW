package ru.spbau.maxim.mobs.Mob

import ru.spbau.maxim.model.ModelReadOnly
import kotlin.reflect.KClass

open class MobDecoratorCombiner(private val mob: Mob): MobDecoratorAbstract(), MobWithEffects {
    private val decorators: MutableList<Node> = mutableListOf()
    private var curMob = mob

    override fun getInnerMob(): Mob {
        return curMob
    }

    override fun addDecorator(decorator: MobDecoratorMutable, timeToLive: Int, model: ModelReadOnly) {
        decorators.add(Node(decorator, timeToLive + model.time()))
    }

    override fun addDecorator(decorator: MobDecoratorMutable) {
        decorators.add(Node(decorator, null))
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

    override fun hasSuch(decoratorClass: KClass<out MobDecoratorMutable>): Boolean {
        return decorators.any { (decorator, _) -> decoratorClass.java.isInstance(decorator) }
    }

    override fun toString(): String {
        return getPosition().toString()
    }
}