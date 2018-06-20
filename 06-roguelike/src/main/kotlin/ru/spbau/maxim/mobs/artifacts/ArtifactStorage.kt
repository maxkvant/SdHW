package ru.spbau.maxim.mobs.artifacts

interface ArtifactStorage: ArtifactsStorageReadOnly {
    fun tryAddArtifact(artifact: Artifact): Boolean

    fun removeArtifact(index: Int): Boolean

    fun choseArtifact(index: Int): Boolean
}

