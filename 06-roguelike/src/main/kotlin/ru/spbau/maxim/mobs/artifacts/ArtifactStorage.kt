package ru.spbau.maxim.mobs.artifacts

interface ArtifactStorage {
    fun getCurrentArtifact(): Artifact?
    fun tryAddArtifact(artifact: Artifact): Boolean
    fun removeArtifact(index: Int): Boolean
    fun choseArtifact(index: Int): Boolean
    fun getArtifacts(): List<Artifact>
}

