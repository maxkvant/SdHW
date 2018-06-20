package ru.spbau.maxim.view.layers

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.terminal.Terminal
import ru.spbau.maxim.model.ModelReadOnly

interface TerminalLayer {
    val size: Size

    val offset: Position

    val terminal: Terminal

    fun draw(model: ModelReadOnly)

    fun buildLayer(): Layer = LayerBuilder.newBuilder()
                .size(size)
                .offset(offset)
                .build()

    fun clear() {
        val layer = buildLayer()
        clearBoard(size, layer)
        terminal.pushLayer(layer)
    }
}

fun clearBoard(size: Size, layer: Layer) {
    for (col in 0 until size.columns) {
        for (row in 0 until size.rows) {
            layer.setCharacterAt(Position.of(col, row), ' ')
        }
    }
}