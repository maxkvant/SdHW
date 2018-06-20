package ru.spbau.maxim.view.layers

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.terminal.Terminal
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.model.field.Cell

class FieldLayer(
        override val size: Size,
        override val offset: org.codetome.zircon.api.Position,
        override val terminal: Terminal
) : TerminalLayer {

    override fun draw(model: ModelReadOnly) {
        val layer= buildLayer()
        drawField(layer, model)
        terminal.pushLayer(layer)
    }

    private fun drawField(layer: Layer, model: ModelReadOnly) {
        val playerPos = model.getPlayerReadOnly().getPosition()
        val field = model.getField()

        for (row in 0 until size.rows) {
            for (col in 0 until size.columns) {
                val fieldPos = Position(playerPos.i + (row - size.rows / 2), playerPos.j + (col - size.columns / 2))
                val c = if (field.inside(fieldPos)) {
                    when {
                        fieldPos == playerPos && !model.getPlayerReadOnly().isDead() -> '@'
                        model.getMobReadOnly(fieldPos) != null -> '&'
                        model.getCell(fieldPos) == Cell.WALL -> '#'
                        model.getCell(fieldPos) == Cell.SPACE -> '.'
                        model.getCell(fieldPos) == Cell.ROUTE -> '.'
                        else -> ' '
                    }
                } else {
                    ' '
                }
                layer.setCharacterAt(org.codetome.zircon.api.Position.of(col, row), c)
            }
        }
    }
}