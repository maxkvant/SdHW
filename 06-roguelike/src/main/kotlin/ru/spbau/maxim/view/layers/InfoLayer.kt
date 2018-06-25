package ru.spbau.maxim.view.layers

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.graphics.Layer
import ru.spbau.maxim.model.ModelReadOnly

/**
 * layer of information about player:
 * his hp, attack, defence,
 * how many turns have he done
 */
class InfoLayer(
        override val size: Size,
        override val offset: org.codetome.zircon.api.Position,
        override val terminal: org.codetome.zircon.api.terminal.Terminal
) : TerminalLayer {

    override fun draw(model: ModelReadOnly) {
        val layer = buildBaseLayer()
        drawInfo(layer, model)
        terminal.pushLayer(layer)
    }

    private fun drawInfo(layer: Layer, model: ModelReadOnly) {
        val player = model.getPlayerReadOnly()

        val messages = listOf(
                "hp: ${player.getHp()}",
                "turns: ${model.turns()}",
                "attack: ${player.attack}",
                "defence: ${player.defence}", "-----"
        )

        clearBoard(size, layer)
        for (i in messages.indices) {
            layer.putText(messages[i], Position.of(1, i * 2 + 1))
        }
    }
}