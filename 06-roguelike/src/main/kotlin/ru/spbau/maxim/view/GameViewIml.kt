package ru.spbau.maxim.view

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.builder.TerminalBuilder
import org.codetome.zircon.api.resource.CP437TilesetResource
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.Position as Pos
import ru.spbau.maxim.view.GameView.*
import java.util.function.Consumer



class GameViewIml(private val width: Int, private val height: Int): GameView {
    private val inputListeners: MutableList<InputListener> = mutableListOf()

    private val terminal = TerminalBuilder
            .newBuilder()
            .initialTerminalSize(Size.of(height, width))
            .font(CP437TilesetResource.WANDERLUST_16X16.toFont())
            .buildTerminal()

    init {
        terminal.onInput(Consumer { input ->
            inputListeners.forEach {
                it.onInput(input)
            }
        })
    }


    override fun addInputListener(listener: InputListener) {
        inputListeners.add(listener)
    }

    override fun draw(model: ModelReadOnly) {
        val playerPos = model.getPlayerReadOnly().getPosition()
        val field = model.getField()

        val layer = LayerBuilder.newBuilder()
                .size(Size(width, height))
                .build()


        for (row in 0 until height) {
            for (col in 0 until width) {
                val fieldPos = Pos(playerPos.i + (row - height / 2), playerPos.j + (col - width / 2))
                val c = if (field.inside(fieldPos)) {
                    when {
                        fieldPos == playerPos -> '@'
                        model.getMobReadOnly(fieldPos) != null -> '&'
                        model.getCell(fieldPos) == Cell.WALL -> '#'
                        model.getCell(fieldPos) == Cell.SPACE -> '.'
                        model.getCell(fieldPos) == Cell.ROUTE -> '.'
                        else -> ' '
                    }
                } else {
                    ' '
                }
                layer.setCharacterAt(Position.of(col, row), c)
            }
        }
        terminal.pushLayer(layer)
        terminal.flush()
    }

    override fun close() {
        terminal.close()
    }
}