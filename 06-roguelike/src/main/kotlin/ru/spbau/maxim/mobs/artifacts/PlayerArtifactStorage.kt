package ru.spbau.maxim.mobs.artifacts

class PlayerArtifactStorage: ArtifactStorage {
    override val capacity = 4

    private val artifacts: Array<Artifact?> = Array(capacity, { null })
    private var currentArtifact: Artifact? = null

    override fun getArtifact(index: Int): Artifact? {
        return artifacts[index]
    }

    override fun getCurrentArtifact(): Artifact? = currentArtifact

    override fun tryAddArtifact(artifact: Artifact): Boolean {
        for (i in 0..capacity) {
            if (artifacts[i] == null) {
                artifacts[i] = artifact
                return true
            }
        }
        return false
    }

    override fun removeArtifact(index: Int): Boolean {
        val success = artifacts[index] != null
        artifacts[index] = null
        return success
    }

    override fun choseArtifact(index: Int): Boolean {
        currentArtifact = artifacts[index]
        return currentArtifact != null
    }
}