package ru.spbau.maxim.controller

import org.codetome.zircon.api.input.Input

interface SubController {
    fun onAction(input: Input): Boolean
}