package ru.spbau.maxim.mobs.artifacts

/**
 * Stores One artifact, is immutable
 */
class OneArtifactStorage(private val artifact: Artifact): ArtifactStorage {
    override val capacity = 1

    override fun getArtifacts(): List<Artifact> = listOf(artifact)

    override fun getCurrentArtifactI(): Int? = 0

    override fun tryAddArtifact(artifact: Artifact): Boolean = false

    override fun removeArtifact(index: Int): Boolean = false

    override fun choseArtifact(index: Int): Boolean = false

    override fun getArtifact(index: Int): Artifact? {
        require(index == 0)
        return artifact
    }
}