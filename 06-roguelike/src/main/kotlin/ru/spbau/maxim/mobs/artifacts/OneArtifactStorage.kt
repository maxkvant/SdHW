package ru.spbau.maxim.mobs.artifacts

class OneArtifactStorage(private val artifact: Artifact): ArtifactStorage {
    override fun getArtifacts(): List<Artifact> = listOf(artifact)

    override fun getCurrentArtifact(): Artifact? = artifact

    override fun tryAddArtifact(artifact: Artifact): Boolean = false

    override fun removeArtifact(index: Int): Boolean = false

    override fun choseArtifact(index: Int): Boolean = false
}