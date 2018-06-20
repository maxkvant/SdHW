package ru.spbau.maxim.mobs.artifacts

interface ArtifactStorage {
    fun getCurrentArtifact(): Artifact?

    fun tryAddArtifact(artifact: Artifact): Boolean

    fun removeArtifact(index: Int): Boolean

    fun choseArtifact(index: Int): Boolean

    val capacity: Int

    fun getArtifact(index: Int): Artifact?

    fun getArtifacts(): List<Artifact> {
        return (0 until capacity).map { i -> getArtifact(i) }.filterNotNull()
    }
}

