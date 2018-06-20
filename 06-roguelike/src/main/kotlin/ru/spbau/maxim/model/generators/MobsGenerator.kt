package ru.spbau.maxim.model.generators

import ru.spbau.maxim.mobs.effects.Freezer
import ru.spbau.maxim.mobs.effects.Helmet
import ru.spbau.maxim.mobs.effects.Knife
import ru.spbau.maxim.mobs.mobCore.*
import ru.spbau.maxim.mobs.MobFactory
import ru.spbau.maxim.mobs.PlayerMobWithEffects
import ru.spbau.maxim.mobs.artifacts.Artifact
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.field.FieldReadOnly
import java.util.*
import kotlin.math.roundToInt

/**
 * Puts mobs of field
 * density is fraction mobs in space cells of field
 */
fun generateMobs(field: FieldReadOnly<Cell>, density: Double): Pair<PlayerMobWithEffects, List<MobWithEffects>> {
    val factory = MobFactory()

    fun generateMob(position: Position): MobWithEffects {
        val i = Random().nextInt() % 4
        val helmet = Artifact({ Helmet() }, "helmet")
        val knife = Artifact({ Knife() }, "knife")
        val freezer = Artifact({ Freezer() }, "freezer")
        return when (i) {
            0 -> factory.enemyNoArtifacts(position)
            1 -> factory.enemyOneArtifact(position, helmet)
            2 -> factory.enemyOneArtifact(position, knife)
            else -> factory.enemyOneArtifact(position, freezer)
        }
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
    positions.shuffle()

    val enemiesPositions: List<Position> = positions.subList(1, resSize)
    val playerPos = positions[0]
    val player = factory.playerMobWithEffects(playerPos)
    val enemies = enemiesPositions.map { generateMob(it) }
    return Pair(player, enemies)
}