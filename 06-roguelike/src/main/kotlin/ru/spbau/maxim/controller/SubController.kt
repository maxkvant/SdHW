package ru.spbau.maxim.controller

import org.codetome.zircon.api.input.Input

/**
 * Interface for player actions handler
 */
interface SubController {
    /**
     * handles input action
     * returns true if model is changed and view should be updated
     */
    fun onAction(input: Input): Boolean
}