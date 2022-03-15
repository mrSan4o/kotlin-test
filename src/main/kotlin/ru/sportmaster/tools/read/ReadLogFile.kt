package ru.sportmaster.tools.read

import java.io.File
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    ReadLogFile.read("")
}

object ReadLogFile {
    fun read(path: String) {
        val lines = Files.readAllLines(File(path).toPath())

        for (line in lines) {
            if (line.contains("[ERROR]")){
                println(line)
            }
        }
    }
}