package ru.spbau.maxim.model.Artifacts

interface ArtifactStorage {
    fun getCurrentArtifact(): Artifact?
    fun tryAddArtifact(artifact: Artifact): Boolean
    fun removeArtifact(index: Int): Boolean
    fun choseArtifact(index: Int): Boolean
}

