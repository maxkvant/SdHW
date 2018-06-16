package ru.spbau.maxim.mobs.Mob

open class MobDecoratorMutaAbstract: MobDecoratorAbstract(), MobDecoratorMutable {
    private var mob: Mob? = null

    override fun getInnerMob(): Mob = mob!!

    override fun setMob(mob: Mob) {
        this.mob = mob
    }
}