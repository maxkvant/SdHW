package ru.spbau.maxim.mobs.actions

import org.junit.Test

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.Matchers.lessThan
import ru.spbau.maxim.mobs.MobFactory
import ru.spbau.maxim.mobs.PlayerMob
import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.mobs.actions.Rest
import ru.spbau.maxim.mobs.effects.Frozen
import ru.spbau.maxim.mobs.mobCore.MobWithEffects
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.field.FieldReadOnly
import ru.spbau.maxim.model.generators.generateField
import ru.spbau.maxim.model.generators.generateMobs
import ru.spbau.maxim.model.impl.ModelImpl

class ActionsTest {
    private val field = FieldReadOnly.of(
        arrayOf(arrayOf(Cell.WALL, Cell.WALL),
                arrayOf(Cell.SPACE, Cell.SPACE),
                arrayOf(Cell.SPACE, Cell.SPACE)
        )
    )

    private val mob = MobFactory.enemyNoArtifacts(Position(1, 0))
    private val playerMob = MobFactory.playerMobWithEffects(Position(1, 1))
    private val model = ModelImpl(field, playerMob, listOf(mob))

    @Test
    fun moveTest() {
        assertThat(model.getMob(Position(1, 0)), `is`(mob))
        assertThat(model.getMob(Position(1, 1)), `is`<MobWithEffects>(playerMob))

        val move1 = Move(mob, Position(1, 0))
        val move2 = Move(mob, Position(2, 0))
        val move3 = Move(mob, Position(2, 1))
        val move4 = Move(mob, Position(1, -1))

        assertThat(move1.validate(mob, model), `is`(false))

        assertThat(move2.validate(mob, model), `is`(true))

        assertThat(move3.validate(mob, model), `is`(false))

        assertThat(move4.validate(mob, model), `is`(false))

        assertThat(mob.getPosition(), `is`(Position(1, 0)))

        move2.executeIfValid(mob, model)

        assertThat(mob.getPosition(), `is`(Position(2, 0)))

        assertThat(model.getMob(Position(2, 0)), `is`(mob))
    }

    @Test
    fun hitSiblingTest() {
        assertThat(model.getMob(Position(1, 0)), `is`(mob))
        assertThat(model.getMob(Position(1, 1)), `is`<MobWithEffects>(playerMob))

        val hit1 = HitSibling(mob.getPosition(), playerMob)

        val hpBefore = playerMob.getHp()

        assertThat(hit1.validate(mob, model), `is`(true))

        assertThat(playerMob.getHp(), `is`(hpBefore))

        hit1.executeIfValid(mob, model)

        assertThat(playerMob.getHp(), `is`(lessThan(hpBefore)))
    }

    @Test
    fun freezeSiblingTest() {
        assertThat(model.getMob(Position(1, 0)), `is`(mob))
        assertThat(model.getMob(Position(1, 1)), `is`<MobWithEffects>(playerMob))

        val hit1 = HitSibling(mob.getPosition(), playerMob)
        val freeze1 = FreezeSibling(mob.getPosition(), playerMob, hit1)

        assertThat(freeze1.validate(mob, model), `is`(true))


        val hp1 = playerMob.getHp()
        assertThat(playerMob.hasDecorator(Frozen::class), `is`(false))
        playerMob.setPlayerTurn(PlayerMob.PlayerTurn.REST)


        model.nextTurn {
            freeze1.executeIfValid(mob, model)
            playerMob.turn(model).executeIfValid(playerMob, model)
        }


        assertThat(playerMob.hasDecorator(Frozen::class), `is`(true))

        val hp2 = playerMob.getHp()

        assertThat(hp2, `is`(hp1))

        model.nextTurn {
            freeze1.executeIfValid(mob, model)
            playerMob.turn(model).executeIfValid(playerMob, model)
        }

        val hp3 = playerMob.getHp()
        assertThat(hp3, `is`(lessThan(hp2)))

        assertThat(playerMob.hasDecorator(Frozen::class), `is`(true))

        for (i in 0 until 10) {
            model.nextTurn {
                playerMob.turn(model).executeIfValid(playerMob, model)
            }
        }
        assertThat(playerMob.hasDecorator(Frozen::class), `is`(false))

    }
}