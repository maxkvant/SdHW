package ru.spbau.maxim.view

import org.codetome.zircon.api.input.Input
import ru.spbau.maxim.model.ModelReadOnly

/**
 * Class for visual representation of Game
 */
interface GameView {
    /**
     * draws current model
     */
    fun draw(model: ModelReadOnly)

    /**
     * adds listener, which can react on player actions
     */
    fun addInputListener(listener: InputListener)

    /**
     * closes game window
     */
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