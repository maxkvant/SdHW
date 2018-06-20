package ru.spbau.maxim.view.layers

import jdk.nashorn.internal.ir.Terminal
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.graphics.Layer
import ru.spbau.maxim.model.ModelReadOnly

class InfoLayer(
        override val size: Size,
        override val offset: org.codetome.zircon.api.Position,
        override val terminal: org.codetome.zircon.api.terminal.Terminal
) : TerminalLayer {

    override fun draw(model: ModelReadOnly) {
        val layer = buildLayer()
        drawInfo(layer, model)
        terminal.pushLayer(layer)
        terminal.flush()
    }

    private fun drawInfo(layer: Layer, model: ModelReadOnly) {
        val player = model.getPlayerReadOnly()

        val messages = listOf(
                "hp: ${player.getHp()}",
                "turns: ${model.time()}",
                "attack: ${player.attack}",
                "defence: ${player.defence}", "-----"
        )

        clearBoard(size, layer)
        for (i in messages.indices) {
            layer.putText(messages[i], Position.of(1, i * 2 + 1))
        }
    }
}