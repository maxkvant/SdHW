package ru.spbau.maxim.view.layers

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.terminal.Terminal
import ru.spbau.maxim.model.ModelReadOnly

class ArtifactsLayer(
        override val size: Size,
        override val offset: org.codetome.zircon.api.Position,
        override val terminal: Terminal
) : TerminalLayer {

    override fun draw(model: ModelReadOnly) {
        val layer = LayerBuilder.newBuilder()
                .size(size)
                .offset(offset)
                .build()

        drawArtifacts(layer, model)
        terminal.pushLayer(layer)
        terminal.flush()
    }

    private fun drawArtifacts(layer: Layer, model: ModelReadOnly) {
        val player = model.getPlayerReadOnly()
        val artifactStorage = player.artifactStorage

        val messages = mutableListOf("Artifacts")

        val currentArtifactI = artifactStorage.getCurrentArtifactI()

        for (i in 0 until artifactStorage.capacity) {
            val artifact = artifactStorage.getArtifact(i)
            val chosen = artifact != null && currentArtifactI == i
            val chosenC = if (chosen) ">" else "*"
            val name = artifact?.name ?: "-"
            messages.add("$chosenC ${i+1}. $name")
        }

        clearBoard(size, layer)
        for (i in messages.indices) {
            layer.putText(messages[i], Position.of(1, i * 2 + 1))
        }
    }
}