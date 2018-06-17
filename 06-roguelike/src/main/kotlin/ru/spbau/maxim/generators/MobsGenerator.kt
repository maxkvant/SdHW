package ru.spbau.maxim.generators

import ru.spbau.maxim.actions.Action
import ru.spbau.maxim.effects.Freezer
import ru.spbau.maxim.effects.Helmet
import ru.spbau.maxim.effects.Knife
import ru.spbau.maxim.effects.StorageEffect
import ru.spbau.maxim.mobs.Mob.*
import ru.spbau.maxim.mobs.SleepingMob
import ru.spbau.maxim.model.Artifacts.Artifact
import ru.spbau.maxim.model.Artifacts.ArtifactStorage
import ru.spbau.maxim.model.Artifacts.EmptyArtifactStorage
import ru.spbau.maxim.model.Artifacts.OneArtifactStorage
import ru.spbau.maxim.model.Mob
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.field.FieldReadOnly
import ru.spbau.maxim.model.impl.ModelImpl
import sun.invoke.empty.Empty
import sun.text.normalizer.NormalizerBase
import java.util.*
import kotlin.math.roundToInt

fun generateMobs(field: FieldReadOnly<Cell>, density: Double, genPlayer: (Position) -> MobWithEffects): Pair<MobWithEffects, List<MobWithEffects>> {
    fun generateMob(position: Position): MobWithEffects {
        val i = Random().nextInt() % 4
        val artifact: Artifact? = when (i) {
            0 -> null
            1 -> Artifact({ Helmet() }, "helmet")
            2 -> Artifact({ Knife() }, "knife")
            else -> Artifact({ Freezer() }, "freezer")
        }
        val artifactStorage = if (artifact != null) OneArtifactStorage(artifact) else EmptyArtifactStorage()
        val mob = object : SleepingMob(position) {
            override val artifactStorage = artifactStorage
        }
        val mobWithEffects: MobWithEffects = MobDecoratorCombiner(mob)
        mobWithEffects.addDecorator(StorageEffect())
        return mobWithEffects
    }

    val positions: MutableList<Position> = mutableListOf()

    for (i in 0 until field.rows) {
        for (j in 0 until field.columns) {
            val pos = Position(i, j)
            if (field[pos] == Cell.SPACE) {
                positions.add(pos)
            }
        }
    }
    val resSize = (positions.size * density).roundToInt()
    Collections.shuffle(positions)

    val enemiesPositions: List<Position> = positions.subList(1, resSize)
    val playerPos = positions[0]
    val player = genPlayer(playerPos)
    val enemies = enemiesPositions.map { generateMob(it) }
    return Pair(player, enemies)
}