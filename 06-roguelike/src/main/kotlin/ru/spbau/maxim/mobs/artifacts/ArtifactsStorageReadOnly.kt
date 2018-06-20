package ru.spbau.maxim.mobs.artifacts

interface ArtifactsStorageReadOnly {
    fun getCurrentArtifact(): Artifact?

    val capacity: Int

    fun getArtifact(index: Int): Artifact?

    fun getArtifacts(): List<Artifact> {
        return (0 until capacity).map { i -> getArtifact(i) }.filterNotNull()
    }
}