package ru.spbau.maxim.mobs.artifacts

import ru.spbau.maxim.mobs.effects.Effect

class Artifact(val effect: () -> Effect, val name: String)