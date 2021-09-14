package ru.sportmaster.tools.read

fun main() {
    RemoveLockFiles.remove("C:\\Users\\aleks\\.android\\avd")
}

object RemoveLockFiles {
    fun remove(path: String) {
        FileScanner.scan(path) { file ->
            if (file.extension == "lock") {
                file.remove()
            }
        }
    }
}