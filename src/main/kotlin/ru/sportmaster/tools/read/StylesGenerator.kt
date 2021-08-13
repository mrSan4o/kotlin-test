package ru.sportmaster.tools.read

import java.io.File

fun main() {
    GenerateColors.main()
}


object ReadFonts {
    val names = listOf("x4l", "x3l", "xxl", "xl", "l", "m", "s", "xs", "xxs", "x3s", "x4s")
    val sizes = listOf(43, 33, 23, 21, 19, 17, 15, 13, 11)
    val lineHeights = listOf(54, 42, 32, 30, 28, 26, 20, 16, 14)
    val letterSpacings = listOf(0.3, 0.2, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1)
    val weights = listOf(300, 400, 500, 700, 900)
    fun main() {
        val newStyleXmlPath =
            "E:\\Development\\Projects\\AndroidProjects\\sportmaster\\flexo-components-android\\core\\src\\main\\res\\values\\fonts.xml"
        val oldStyleXmlPath =
            "E:\\Development\\Projects\\AndroidProjects\\sportmaster\\flexo-components-android\\core\\src\\main\\res\\values\\fonts_deprecated.xml"

        val newStylesMap = parseStylesMap(newStyleXmlPath)

        newStylesMap.forEach { name, style ->
            println("FontItem(R.style.$name, \"$name\"),")
        }

        // val oldStylesMap = parseStylesMap(oldStyleXmlPath)
        // newStylesMap.forEach { name, style -> println("$name : $style") }
        // println()
        // print(oldStylesMap, newStylesMap)
        //
        // println()
        // println()
        // val mappings = mappingOldToNew(oldStylesMap, newStylesMap)
        // mappings.forEach { println("<style name=\"${it.first}\" parent=\"${it.second}\"/>") }
    }

    private fun mappingOldToNew(
        oldStylesMap: Map<String, Style>,
        newStylesMap: Map<String, Style>
    ): List<Pair<String, String>> {
        return oldStylesMap.entries
            .map { (oldName, oldStyle) ->
                val oldFontFamily = oldStyle.fontFamily
                val oldTextSize = oldStyle.textSize

                val styleEntry = newStylesMap.entries
                    .find { it.value.fontFamily == oldFontFamily && it.value.textSize == oldTextSize }
                    ?: throw IllegalStateException("!!! not found for $oldName $oldStyle")

                Pair<String, String>(oldName, styleEntry.key)
            }
    }

    private fun print(
        oldStylesMap: Map<String, Style>,
        newStylesMap: Map<String, Style>
    ) {
        oldStylesMap.forEach { oldName, oldStyle ->
            val oldFontFamily = oldStyle.fontFamily
            val oldTextSize = oldStyle.textSize

            val styleEntry = newStylesMap.entries
                .find { it.value.fontFamily == oldFontFamily && it.value.textSize == oldTextSize }
            if (styleEntry == null) {
                println("!!! not found for $oldName $oldStyle")
            } else {
                println("$oldName > ${styleEntry.key}\nold:$oldStyle\nnew:${styleEntry.value}")
            }
        }
    }

    private fun parseStylesMap(path: String): Map<String, Style> {
        val lines = File(path).readLines().map { it.trim() }
        val map = HashMap<String, Style>()

        var tagBegin = false
        var name = ""
        var fontFamily = ""
        var textSize = ""
        var textStyle = ""
        var lineHeight = ""
        for (line in lines) {

            if (tagBegin) {
                if (line.startsWith("<item")) {
                    val lineName = substringName(line).replace("android:", "")
                    val lineValue = line.substringAfter(">").substringBefore("</")
                    when (lineName) {
                        "fontFamily" -> fontFamily = lineValue
                        "textSize" -> textSize = lineValue
                        "textStyle" -> textStyle = lineValue
                        "lineHeight" -> lineHeight = lineValue
                        else -> println("Unknown: $lineName : $lineValue")
                    }
                }

                val endStyle = line.startsWith("</style>")
                if (endStyle) {
                    map[name] = Style(fontFamily, textSize, textStyle, lineHeight)
                    tagBegin = false
                }
            } else {
                tagBegin = line.startsWith("<style")
                if (tagBegin) {
                    name = substringName(line)
                }
            }
        }

        return map
    }

    private fun substringName(line: String) = line.substringAfter("name=\"").substringBefore("\"")

    data class Style(
        val fontFamily: String,
        val textSize: String,
        val textStyle: String,
        val lineHeight: String
    )

    data class Font(
        val size: Int,
        val weight: Int,
        val lineHeight: Int,
        val letterSpacing: Float
    )
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
