package ru.spbau.maxim.model.Artifacts

import ru.spbau.maxim.mobs.Mob.Effect
import ru.spbau.maxim.mobs.Mob.EffectAbstract

class Artifact(val effect: () -> Effect, val name: String)