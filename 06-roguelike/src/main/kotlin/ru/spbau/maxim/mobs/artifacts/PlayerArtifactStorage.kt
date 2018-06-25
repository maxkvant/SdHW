package ru.spbau.maxim.mobs.artifacts

/**
 * Stores player artifacts, is mutable
 */
class PlayerArtifactStorage: ArtifactStorage {
    override val capacity = 4

    private val artifacts: Array<Artifact?> = Array(capacity, { null })
    private var currentArtifactI: Int? = null

    override fun getCurrentArtifactI(): Int? = currentArtifactI

    override fun getArtifact(index: Int): Artifact? {
        return artifacts[index]
    }

    override fun tryAddArtifact(artifact: Artifact): Boolean {
        for (i in 0 until capacity) {
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
        currentArtifactI = index
        return artifacts[index] != null
    }
}