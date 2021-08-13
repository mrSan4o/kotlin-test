package ru.sportmaster.tools.read

import java.io.File

object ReadColors {
    fun readColorsXml(path: String): List<ColorItem> {
        val lines = File(path).readLines()

        return lines
            .filter { it.contains("<color name=") }
            .map { line ->
                val name = line.substringAfter("<color name=\"").substringBefore("\"")
                val value = line.substringAfter(">").substringBefore("</")
                ColorItem(name, value)
            }
    }
}

data class ColorItem(
    val name: String,
    val value: String
)

val ColorItem.colorTag: String
    get() = "<color name=\"$name\">$value</color>"