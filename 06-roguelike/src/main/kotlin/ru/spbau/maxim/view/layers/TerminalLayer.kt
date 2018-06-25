package ru.spbau.maxim.view.layers

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.terminal.Terminal
import ru.spbau.maxim.model.ModelReadOnly

/**
 * Interface for layer of terminal, that represents part of screen, with top left corner offset
 */
interface TerminalLayer {
    val size: Size

    val offset: Position

    val terminal: Terminal

    /**
     * draw it in terminal, but not to flush
     */
    fun draw(model: ModelReadOnly)

    fun buildBaseLayer(): Layer = LayerBuilder.newBuilder()
                .size(size)
                .offset(offset)
                .build()

    fun clear() {
        val layer = buildBaseLayer()
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