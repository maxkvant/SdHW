package ru.spbau.maxim.mobs

import ru.spbau.maxim.mobs.mobCore.MobAbstract
import ru.spbau.maxim.mobs.mobCore.MobDecoratorCombiner
import ru.spbau.maxim.mobs.mobCore.MobWithEffects
import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.mobs.actions.HitSibling
import ru.spbau.maxim.mobs.actions.Move
import ru.spbau.maxim.mobs.actions.Rest
import ru.spbau.maxim.model.Position
import java.util.*
import kotlin.math.roundToInt
import kotlin.math.sign
import ru.spbau.maxim.mobs.PlayerMob.*
import ru.spbau.maxim.mobs.artifacts.*
import ru.spbau.maxim.mobs.effects.StorageEffect
import ru.spbau.maxim.model.Model

/**
 * Contains that can create
 */
object MobFactory {
    private val enemyHp: Int = 40
    private val playerHp = 100

    fun enemyNoArtifacts(position: Position): MobWithEffects {
        val artifactStorage = EmptyArtifactStorage
        val baseMob = Enemy(enemyHp, position, artifactStorage)
        return withStorageEffect(
                MobDecoratorCombiner(baseMob)
        )
    }

    fun enemyOneArtifact(position: Position, artifact: Artifact): MobWithEffects {
        val artifactStorage = OneArtifactStorage(artifact)
        val baseMob = Enemy(enemyHp, position, artifactStorage)
        return withStorageEffect(
                MobDecoratorCombiner(baseMob)
        )
    }

    fun playerMobWithEffects(position: Position): PlayerMobWithEffects {
        val baseMob = PlayerMobImpl(playerHp, position)
        return withStorageEffect(
                object : MobDecoratorCombiner(baseMob), PlayerMobWithEffects {
                    override fun setPlayerTurn(playerTurn: PlayerTurn) {
                        baseMob.setPlayerTurn(playerTurn)
                    }
                }
        )
    }

    private fun <T: MobWithEffects> withStorageEffect(mob: T): T {
        mob.addDecorator(StorageEffect())
        return mob
    }

    private class Enemy(maxHp: Int, position: Position, storage: ArtifactStorage) : MobAbstract(maxHp, position) {
        override val artifactStorage: ArtifactStorage = storage
        override val defence: Int = 1
        override val attack: Int = 7

        override fun turn(env: Model): Action {
            val playerMob = env.getPlayer()
            val playerPos = playerMob.getPosition()

            val myPos = getPosition()
            val dist = myPos.dist(playerPos)

            val rnd = Random()

            when {
                dist == 1 -> return HitSibling(myPos, playerMob)
                dist <= 10 -> {
                    val di = sign(playerPos.i - myPos.i + .0).roundToInt()
                    val dj = sign(playerPos.j - myPos.j + .0).roundToInt()


                    val posTo = if ((dj == 0 || rnd.nextBoolean()) && di != 0) {
                        Position(myPos.i + di, myPos.j)
                    } else {
                        Position(myPos.i, myPos.j + dj)
                    }
                    return Move(this, posTo)
                }
                else -> {
                    val d = (rnd.nextInt(2) * 2 - 1)
                    return if (rnd.nextBoolean()) {
                        Move(this, Position(myPos.i + d, myPos.j))
                    } else {
                        Move(this, Position(myPos.i, myPos.j + d))
                    }
                }
            }
        }
    }

    private class PlayerMobImpl(maxHp: Int, position: Position) : MobAbstract(maxHp, position), PlayerMob {
        private var playerTurn = PlayerTurn.REST

        override val artifactStorage: ArtifactStorage = PlayerArtifactStorage()
        override val defence: Int
            get() = 2
        override val attack: Int
            get() = 30

        override fun setPlayerTurn(playerTurn: PlayerTurn) {
            this.playerTurn = playerTurn
        }

        override fun turn(env: Model): Action {
            val pos = getPosition()
            val posTo = when (playerTurn) {
                PlayerTurn.REST  -> pos
                PlayerTurn.RIGHT -> Position(pos.i, pos.j + 1)
                PlayerTurn.UP    -> Position(pos.i - 1, pos.j)
                PlayerTurn.LEFT  -> Position(pos.i, pos.j - 1)
                PlayerTurn.DOWN  -> Position(pos.i + 1, pos.j)
            }
            return if (posTo == pos) {
                Rest
            } else {
                val victim = env.getMob(posTo)
                if (victim == null) Move(this, posTo) else HitSibling(pos, victim)
            }
        }
    }
}