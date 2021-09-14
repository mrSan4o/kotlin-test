package ru.sportmaster.tools.read

import java.io.File

object FileScanner {
    fun collect(path: String, ifFile: (File) -> Boolean): List<File> {
        val result = ArrayList<File>()

        collect(File(path), ifFile, result)

        return result
    }

    private fun collect(file: File, ifFile: (File) -> Boolean, result: ArrayList<File>) {
        if (file.isDirectory) {
            val files = file.listFiles().orEmpty()
            if (files.isNotEmpty()) {
                files.forEach { f ->
                    collect(f, ifFile, result)
                }
            }
        } else {
            if (ifFile(file)) {
                result.add(file)
            }
        }
    }

    fun scan(path: String, onFile: (File) -> Unit) {
        val file = File(path)

        scan(file, onFile)
    }

    private fun scan(file: File, onFile: (File) -> Unit) {
        if (file.isDirectory) {
            val files = file.listFiles().orEmpty()
            if (files.isNotEmpty()) {
                files.forEach { scan(it, onFile) }
            }
        } else {
            onFile(file)
        }
    }
}

fun File.remove(){
    if (this.delete()){
        println("File removed ${this.absolutePath}")
    }else{
        println("Error remove file ${this.absolutePath}")
    }
}

fun File.makeDirs() {
    if (!this.mkdirs()) println("dir $this not created")
}