package ru.spbau.maxim.model.Artifacts

class EmptyArtifactStorage: ArtifactStorage {
    override fun getCurrentArtifact(): Artifact? = null

    override fun tryAddArtifact(artifact: Artifact): Boolean = false

    override fun removeArtifact(index: Int): Boolean = false

    override fun choseArtifact(index: Int): Boolean = false
}