package ru.spbau.maxim.view

import org.codetome.zircon.api.input.Input
import ru.spbau.maxim.model.ModelReadOnly

interface GameView {
    fun draw(model: ModelReadOnly)

    fun addInputListener(listener: InputListener)

    fun close()

    interface InputListener {
        fun onInput(input: Input)

        companion object {
            fun of(f: (Input) -> Unit): InputListener {
                return object: InputListener {
                    override fun onInput(input: Input) = f(input)
                }
            }
        }
    }
}