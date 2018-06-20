package ru.spbau.maxim.mobs.artifacts

/**
 * Interface for read only version of ArtifactStorage
 */
interface ArtifactsStorageReadOnly {
    fun getCurrentArtifact(): Artifact? {
        val i = getCurrentArtifactI()
        return if (i != null) getArtifact(i) else null
    }

    fun getCurrentArtifactI(): Int?

    val capacity: Int

    fun getArtifact(index: Int): Artifact?

    fun getArtifacts(): List<Artifact> {
        return (0 until capacity).map { i -> getArtifact(i) }.filterNotNull()
    }
}