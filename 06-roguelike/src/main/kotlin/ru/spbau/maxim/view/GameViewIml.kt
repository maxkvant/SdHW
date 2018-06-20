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
import ru.spbau.maxim.view.layers.ArtifactsLayer
import ru.spbau.maxim.view.layers.FieldLayer
import ru.spbau.maxim.view.layers.InfoLayer
import ru.spbau.maxim.view.layers.TerminalLayer
import java.util.function.Consumer

class GameViewIml(width: Int, height: Int): GameView {
    private val inputListeners: MutableList<InputListener> = mutableListOf()
    private val size = Size.of(width, height)
    private val terminal = TerminalBuilder
            .newBuilder()
            .initialTerminalSize(size)
            .font(CP437TilesetResource.WANDERLUST_16X16.toFont())
            .buildTerminal()

    private val barWidth = 15

    private val fieldLayer: TerminalLayer = FieldLayer(
            size = Size.of(size.columns - barWidth, size.rows),
            offset = Position.of(0, 0),
            terminal = terminal
    )

    private val infoLayer: TerminalLayer = InfoLayer(
            size = Size.of(barWidth, 15),
            offset = Position.of(size.columns - barWidth, 0),
            terminal = terminal
    )

    private val artifactsLayer: TerminalLayer = ArtifactsLayer(
            size = Size.of(barWidth, size.rows - infoLayer.size.rows),
            offset = Position.of(size.columns - barWidth, infoLayer.size.rows),
            terminal = terminal
    )


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
        fieldLayer.draw(model)
        infoLayer.draw(model)
        artifactsLayer.draw(model)
        terminal.flush()
    }

    override fun close() {
        terminal.close()
    }
}