package ru.sportmaster.tools.read

import java.io.File

fun main() {
    GenerateColors.main()
}


object ReadSpaces {
    val flexoSvgPath = "D:\\FROM INET\\@Opera\\_SPACES.FLEXO"

    fun main() {
        val files = File(flexoSvgPath).listFiles()
        for (file in files) {
            println(file)
            println(file.readText())
        }
    }
}

object GenerateColors {
    val flexoSvgPath = "D:\\FROM INET\\@Opera\\_COLORS.FLEXO"
    val colorsXmlPath =
        "E:\\Development\\Projects\\AndroidProjects\\sportmaster\\flexo-components-android\\core\\src\\main\\res\\values\\colors.xml"

    fun main() {
        val colorsXmlColors = ReadColors.readColorsXml(colorsXmlPath)

        colorsXmlColors.forEach { item ->
            println("ColorItem(R.color.${item.name.toLowerCase()}, \"${item.name.toUpperCase()}\"),")
        }
    }

    fun main3() {
        val colorsXmlColors = ReadColors.readColorsXml(colorsXmlPath)
        val flexoGroupedColors = readColorsFromSvgFlexo(flexoSvgPath)

        val mergedColors = merge(flexoGroupedColors, colorsXmlColors)
        mergedColors.forEach { println(it.colorTag) }
    }

    private fun merge(
        flexoGroupedColors: Map<String, Map<String, ColorItem>>,
        colorsXmlColors: List<ColorItem>
    ): List<ColorItem> {

        return colorsXmlColors
            .map { data ->
                val flexoValue = flexoGroupedColors.entries
                    .flatMap { it.value.values }
                    .find { it.value.equals(data.value, true) }
                    ?.name
                    ?.let { "@color/$it" }
                ColorItem(data.name, flexoValue ?: data.value)
            }
    }

    fun mai3n() {

        val flexoGroupedColors = readColorsFromSvgFlexo(flexoSvgPath)

        flexoGroupedColors.entries.sortedBy { it.key }

            .forEach { entry ->
                val colorsMap = entry.value
                println()
                println("<!-- ${entry.key} - ${keyName(entry.key)} -->")
                colorsMap.entries.sortedBy { it.key.substring(1).toIntOrNull() ?: 0 }
                    .map { it.value.colorTag }
                    .forEach { println(it) }
            }
    }

    private fun readColorsFromSvgFlexo(pathFolder: String): Map<String, Map<String, ColorItem>> {
        val files = File(pathFolder).listFiles()

        val colors = HashMap<String, MutableMap<String, ColorItem>>()
        for (file in files) {
            val name = file.name.substringBefore(".")
            if (name.length !in 2..3 || name[1].toString().toIntOrNull() ?: -1 !in 0..10) continue
            val text = file.readText()
            val tagStart = "<rect"
            val prefix = "fill=\""

            val colorValue = text.substringAfter(tagStart).substringAfter(prefix).substringBefore("\"")

            val colorMap = colors.getOrPut(name[0].toString()) { HashMap<String, ColorItem>() }
            colorMap[name] = ColorItem(name, colorValue)
        }
        return colors
    }



    private fun keyName(key: String): String {
        return when (key) {
            "a" -> "black"
            "b" -> "red"
            "c" -> "orange"
            "d" -> "yellow"
            "e" -> "green"
            "f" -> "blue"
            else -> "unknown"
        }
    }


}
