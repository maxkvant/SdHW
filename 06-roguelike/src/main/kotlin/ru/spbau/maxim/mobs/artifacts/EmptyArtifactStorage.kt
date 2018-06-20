package ru.spbau.maxim.mobs.artifacts

class EmptyArtifactStorage: ArtifactStorage {
    override val capacity: Int
        get() = 0

    override fun getCurrentArtifactI(): Int? = null

    override fun getArtifacts(): List<Artifact> = listOf()

    override fun tryAddArtifact(artifact: Artifact): Boolean = false

    override fun removeArtifact(index: Int): Boolean = false

    override fun choseArtifact(index: Int): Boolean = false
    
    override fun getArtifact(index: Int): Artifact? = null
}