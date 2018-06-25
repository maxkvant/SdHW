package ru.spbau.maxim.mobs.artifacts

/**
 * Interface for something, that stores mob's artifacts.
 * Usually wraps an array of Artifact?
 */
interface ArtifactStorage: ArtifactsStorageReadOnly {
    /**
     * Tries to add an artifact to storage it is valid,
     * returns true if an artifact was added, false otherwise
     */
    fun tryAddArtifact(artifact: Artifact): Boolean

    /**
     * Tries to remove an artifact at index
     * returns true if an artifact was removed, false otherwise
     */
    fun removeArtifact(index: Int): Boolean

    /**
     * Tries to sets Mobs's current artifact to artifact at index in storage
     */
    fun choseArtifact(index: Int): Boolean
}

