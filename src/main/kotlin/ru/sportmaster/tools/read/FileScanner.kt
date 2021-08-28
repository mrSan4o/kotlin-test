package ru.sportmaster.tools.read

import java.io.File

object FileScanner {
    fun scan(path: String, onFile: (File) -> Unit) {
        val file = File(path)

        scan(file, onFile)
    }

    private fun scan(file: File, onFile: (File) -> Unit) {
        if (file.isDirectory) {
            val files = file.listFiles().orEmpty()
            if (files.isNotEmpty()) {
                files .forEach { scan(it, onFile) }
            }
        } else {
            onFile(file)
        }
    }
}

fun File.makeDirs() {
    if (!this.mkdirs()) println("dir $this not created")
}