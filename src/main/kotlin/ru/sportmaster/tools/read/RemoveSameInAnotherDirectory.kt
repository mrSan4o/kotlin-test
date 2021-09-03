package ru.sportmaster.tools.read

fun main() {
    RemoveSameInAnotherDirectory.remove(
        sourceFolder = "E:\\Development\\Projects\\AndroidProjects\\sportmaster\\mobexp\\mobexp-android-app-2\\modules\\app-common\\src\\test",
        removeInFolder = "E:\\Development\\Projects\\AndroidProjects\\sportmaster\\mobexp\\mobexp-android-app-2\\app\\src\\test"

    )
}

object RemoveSameInAnotherDirectory {
    fun remove(sourceFolder: String, removeInFolder: String) {
        val sourceFiles = FileScanner.collect(
            path = sourceFolder,
            ifFile = { it.extension == "kt" }
        )

        println("Source:")
        sourceFiles.forEach { println(it) }
        println()
        println()
        println("Target:")
        FileScanner.scan(removeInFolder) { file ->
            if (file.extension == "kt") {
                val targetFilePath = file.absolutePath.removePrefix(removeInFolder)

                val exist = sourceFiles.any { it.absolutePath.removePrefix(sourceFolder) == targetFilePath }

                if (exist) {
                    println(targetFilePath)

                    // if (file.delete()) println("REMOVED: $targetFilePath")
                }
            }
        }
    }
}