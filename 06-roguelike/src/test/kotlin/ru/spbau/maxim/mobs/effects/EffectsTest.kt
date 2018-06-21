package ru.spbau.maxim.mobs.effects

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.junit.Test
import ru.spbau.maxim.mobs.PlayerMob
import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.mobs.actions.Rest
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.generators.generateField
import ru.spbau.maxim.model.generators.generateMobs
import ru.spbau.maxim.model.impl.ModelImpl

class EffectsTest {
    private val field = generateField(140, 140)
    fun genModel(): Model {
        val (player, enemies) = generateMobs(field, 0.02)
        return ModelImpl(field, player, enemies)
    }

    @Test
    fun freezerTest() {
        val model = genModel()
        val player = model.getPlayer()
        player.setPlayerTurn(PlayerMob.PlayerTurn.DOWN)

        val restAction: Action = Rest

        assertThat(player.turn(model), `is`(not(restAction)))
        player.addDecorator(Frozen())

        assertThat(player.turn(model), `is`(restAction))
    }

    @Test
    fun helmetTest() {
        val model = genModel()
        val player = model.getPlayer()
        player.setPlayerTurn(PlayerMob.PlayerTurn.DOWN)

        val defenceBefore = player.defence
        player.addDecorator(Helmet())
        player.turn(model)
        val defenceAfter = player.defence
        assertThat(defenceAfter, `is`(greaterThan(defenceBefore)))
    }

    @Test
    fun knifeTest() {
        val model = genModel()
        val player = model.getPlayer()
        player.setPlayerTurn(PlayerMob.PlayerTurn.DOWN)

        val attackBefore = player.attack
        player.addDecorator(Knife())
        player.turn(model)
        val attackAfter = player.attack
        assertThat(attackAfter, `is`(greaterThan(attackBefore)))
    }
}
