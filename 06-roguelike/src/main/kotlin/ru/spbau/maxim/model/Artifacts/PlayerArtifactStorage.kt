package ru.spbau.maxim.model.Artifacts

class PlayerArtifactStorage: ArtifactStorage {
    private val size = 4
    private val artifacts: Array<Artifact?> = Array(size, {null})
    private var currentArtifact: Artifact? = null

    fun getArtifact(index: Int): Artifact? {
        return artifacts[index]
    }

    override fun getCurrentArtifact(): Artifact? = currentArtifact

    override fun tryAddArtifact(artifact: Artifact): Boolean {
        for (i in 0..size) {
            if (artifacts[i] == null) {
                artifacts[i] = artifact
                return true
            }
        }
        return false
    }

    override fun removeArtifact(index: Int): Boolean {
        val sucess = artifacts[index] != null
        artifacts[index] = null
        return sucess
    }

    override fun choseArtifact(index: Int): Boolean {
        currentArtifact = artifacts[index]
        return currentArtifact != null
    }

    override fun getArtifacts(): List<Artifact> = artifacts.filterNotNull()
}