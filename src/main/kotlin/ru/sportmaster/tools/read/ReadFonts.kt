package ru.sportmaster.tools.read

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.File
import kotlin.math.abs

object ReadFonts {
    @JvmStatic
    fun main(args: Array<String>) {
        val oldStyleXmlPath =
            "E:\\Development\\Projects\\AndroidProjects\\sportmaster\\flexo-components-android\\core\\src\\main\\res\\values\\fonts_deprecated.xml"

        val xmlStylesMap =
            parseXmlStylesMap("/Users/aleknazarov/StudioProjects/sportmaster/flexo-components-android/core/src/main/res/values/fonts.xml")
        println("xml : ${xmlStylesMap.size}")

        val jsonStylesMap =
            parseJsonStylesMap("/Users/aleknazarov/IdeaProjects/sportmaster/kotlin-test/src/main/kotlin/ru/sportmaster/tools/read/fonts.json")
        println("json : ${jsonStylesMap.size}")

        printCompose(jsonStylesMap)

        val materialStylesMap = buildMaterialStyles()

        compare(materialStylesMap, jsonStylesMap)

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

    private fun compare(
        materialStylesMap: Map<String, Style>,
        jsonStylesMap: Map<String, Style>
    ) {
        for (materialStyle in materialStylesMap.entries) {
            val (name, style) = materialStyle
            val flexoStyleEntries = jsonStylesMap.entries
            val flexoStyleEntry: Map.Entry<String, Style>? = find(flexoStyleEntries, materialStyle)
            if (flexoStyleEntry == null) {
                println("Style not found: $name : $style")
                continue
            }
            val (flexoName, flexoStyle) = flexoStyleEntry

            println("Match \"$name\" -> \"$flexoName\"\nmaterial: $style\nflexo: $flexoStyle")
            println()
        }
    }

    private fun find(
        flexoStyleEntries: Collection<Map.Entry<String, Style>>,
        styleEntry: Map.Entry<String, Style>
    ): Map.Entry<String, Style>? {
        val materialStyleName = styleEntry.key
        val materialStyle = styleEntry.value
        val materialTextSize = materialStyle.textSize
        val materialWeight = materialStyle.fontWeight
        val matchStyleEntry: Map.Entry<String, Style>? = null

        var leftMost: Map.Entry<String, Style>? = null
        var rightMost: Map.Entry<String, Style>? = null
        for (flexoStyleEntry in flexoStyleEntries) {
            val (flexoName, flexoStyle) = flexoStyleEntry
            val diffTextSize = (materialTextSize - flexoStyle.textSize)

            if (diffTextSize > 0) {
                val diffLeftTextSize = abs(materialTextSize - (leftMost?.value?.textSize ?: 0))
                if (diffLeftTextSize > abs(diffTextSize)) {
                    leftMost = flexoStyleEntry
                } else if (diffLeftTextSize == abs(diffTextSize)) {
                    val diffWeight = abs(materialWeight - flexoStyle.fontWeight)
                    val diffLeftWeight = abs(materialWeight - (leftMost?.value?.fontWeight ?: 0))
                    if (diffLeftWeight > diffWeight) {
                        leftMost = flexoStyleEntry
                    } else if (diffLeftWeight == diffWeight){
                        if (materialStyle.textAllCaps == flexoStyle.textAllCaps){
                            leftMost = flexoStyleEntry
                        }
                    }

                }
            } else if (diffTextSize < 0) {
                val diffRightTextSize = abs(materialTextSize - (rightMost?.value?.textSize ?: 0))
                if (diffRightTextSize > abs(diffTextSize)) {
                    rightMost = flexoStyleEntry
                } else if (diffRightTextSize == abs(diffTextSize)) {
                    val diffWeight = abs(materialWeight - flexoStyle.fontWeight)
                    val diffrightWeight = abs(materialWeight - (rightMost?.value?.fontWeight ?: 0))
                    if (diffrightWeight > diffWeight) {
                        rightMost = flexoStyleEntry
                    } else if (diffrightWeight == diffWeight){
                        if (materialStyle.textAllCaps == flexoStyle.textAllCaps){
                            rightMost = flexoStyleEntry
                        }
                    }

                }
            } else {
                return flexoStyleEntry
            }
        }
//        println("   Search for $materialStyleName : $materialStyle\n   left: $leftMost\n   right: $rightMost")
        return leftMost
//        return if (abs(materialTextSize - (leftMost?.value?.textSize ?: 0)) >
//            abs(materialTextSize - (rightMost?.value?.textSize ?: 0))
//        ) {
//            rightMost
//        } else {
//            leftMost
//        }
    }

    private fun buildMaterialStyles(): Map<String, Style> {
        return mapOf(
            "H1" to Style(
                fontFamily = "roboto",
                textSize = 96,
                textStyle = null,
                lineHeight = 0,
                fontWeight = weightToInt("light"),
                textAllCaps = false,
                letterSpacing = -1.5f
            ),
            "H2" to Style(
                fontFamily = "roboto",
                textSize = 60,
                textStyle = null,
                lineHeight = 0,
                fontWeight = weightToInt("light"),
                textAllCaps = false,
                letterSpacing = -0.5f
            ),
            "H3" to Style(
                fontFamily = "roboto",
                textSize = 48,
                textStyle = null,
                lineHeight = 0,
                fontWeight = weightToInt("regular"),
                textAllCaps = false,
                letterSpacing = 0f
            ),
            "H4" to Style(
                fontFamily = "roboto",
                textSize = 34,
                textStyle = null,
                lineHeight = 0,
                fontWeight = weightToInt("regular"),
                textAllCaps = false,
                letterSpacing = 0.25f
            ),
            "H5" to Style(
                fontFamily = "roboto",
                textSize = 24,
                textStyle = null,
                lineHeight = 0,
                fontWeight = weightToInt("regular"),
                textAllCaps = false,
                letterSpacing = 0f
            ),
            "H6" to Style(
                fontFamily = "roboto",
                textSize = 20,
                textStyle = null,
                lineHeight = 0,
                fontWeight = weightToInt("medium"),
                textAllCaps = false,
                letterSpacing = 0.15f
            ),
            "Subtitle1" to Style(
                fontFamily = "roboto",
                textSize = 16,
                textStyle = null,
                lineHeight = 0,
                fontWeight = weightToInt("regular"),
                textAllCaps = false,
                letterSpacing = 0.15f
            ),
            "Subtitle2" to Style(
                fontFamily = "roboto",
                textSize = 14,
                textStyle = null,
                lineHeight = 0,
                fontWeight = weightToInt("medium"),
                textAllCaps = false,
                letterSpacing = 0.1f
            ),
            "Body1" to Style(
                fontFamily = "roboto",
                textSize = 16,
                textStyle = null,
                lineHeight = 0,
                fontWeight = weightToInt("regular"),
                textAllCaps = false,
                letterSpacing = 0.5f
            ),
            "Body2" to Style(
                fontFamily = "roboto",
                textSize = 14,
                textStyle = null,
                lineHeight = 0,
                fontWeight = weightToInt("regular"),
                textAllCaps = false,
                letterSpacing = 0.25f
            ),
            "Button" to Style(
                fontFamily = "roboto",
                textSize = 14,
                textStyle = null,
                lineHeight = 0,
                fontWeight = weightToInt("medium"),
                textAllCaps = true,
                letterSpacing = 1.25f
            ),
            "Caption" to Style(
                fontFamily = "roboto",
                textSize = 12,
                textStyle = null,
                lineHeight = 0,
                fontWeight = weightToInt("regular"),
                textAllCaps = false,
                letterSpacing = 0.4f
            ),
            "Overline" to Style(
                fontFamily = "roboto",
                textSize = 10,
                textStyle = null,
                lineHeight = 0,
                fontWeight = weightToInt("regular"),
                textAllCaps = true,
                letterSpacing = 1.5f
            )
        )
    }

    private fun weightToInt(name: String): Int {
        return when (name.lowercase()) {
            "light" -> 300
            "medium" -> 500
            "regular" -> 400
            else -> {
                println("Unknown weight : $name")
                400
            }
        }
    }

    private fun printCompose(newStylesMap: Map<String, Style>) {
        newStylesMap.forEach { name: String, style: Style ->
            //            println("FontItem(R.style.$name, \"$name\"),")
            val fontSize = style.textSize
            val lineHeight = style.lineHeight
            val fontWeight = style.fontWeight
            val letterSpacing = style.letterSpacing
            println(
                """
                        val $name = baseTextStyle(
                            fontSize = $fontSize.sp,
                            lineHeight = $lineHeight.sp,
                            fontWeight = FontWeight.W$fontWeight,
                            letterSpacing = $letterSpacing.sp
                        )
    
                """.trimIndent()
            )
        }
    }


    val names = listOf("x4l", "x3l", "xxl", "xl", "l", "m", "s", "xs", "xxs", "x3s", "x4s")
    val sizes = listOf(43, 33, 23, 21, 19, 17, 15, 13, 11)
    val lineHeights = listOf(54, 42, 32, 30, 28, 26, 20, 16, 14)
    val letterSpacings = listOf(0.3, 0.2, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1)
    val weights = listOf(300, 400, 500, 700, 900)

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

    private fun parseJsonStylesMap(path: String): Map<String, Style> {
        val text = File(path).readText()
        val element: JsonElement = JsonParser.parseString(text)
        if (element !is JsonObject) {
            return emptyMap()
        }

        return element.keySet()
            .associateBy(
                keySelector = { it },
                valueTransform = {
                    toStyle(element.get(it) as JsonObject)
                }
            )
    }

    private fun toStyle(json: JsonObject): Style {


        /*
            "fontFamily": "Roboto",
            "fontSize": "11px",
            "fontWeight": 900,
            "textStyle": "normal",
            "lineHeight": "16px",
            "letterSpacing": "1px"
        * */
        return Style(
            fontFamily = json.get("fontFamily")?.asString,
            textSize = json.get("fontSize").asString.toStyleInt(),
            textStyle = json.get("textStyle")?.asString,
            lineHeight = json.get("lineHeight").asString.toStyleInt(),
            fontWeight = json.get("fontWeight").asString.toIntOrNull() ?: 0,
            textAllCaps = false,
            letterSpacing = json.get("letterSpacing").asString.toStyleFloat()
        )
    }

    private fun String.toStyleInt(): Int =
        replaceStyle()
            .toIntOrNull() ?: 0

    private fun String.toStyleFloat(): Float =
        replaceStyle()
            .toFloatOrNull() ?: 0f

    private fun String.replaceStyle() = this.replace("px", "")
        .replace("sp", "")
        .replace("dp", "")


    private fun parseXmlStylesMap(path: String): Map<String, Style> {
        val lines = File(path).readLines().map { it.trim() }
        val map = HashMap<String, Style>()

        var tagBegin = false
        var name = ""
        var fontFamily = ""
        var textSize = 0
        var textStyle = ""
        var lineHeight = 0
        var textAllCaps = false
        var fontWeight = 0
        for (line in lines) {

            if (tagBegin) {
                if (line.startsWith("<item")) {
                    val lineName = substringName(line).replace("android:", "")
                    val lineValue = line.substringAfter(">").substringBefore("</")
                    when (lineName) {
                        "fontFamily" -> fontFamily = lineValue
                        "textSize" -> textSize = lineValue.toStyleInt()
                        "textStyle" -> textStyle = lineValue
                        "lineHeight" -> lineHeight = lineValue.toStyleInt()
                        "textAllCaps" -> textAllCaps = lineValue == "true"
                        "fontWeight" -> fontWeight = lineValue.toIntOrNull() ?: 0
                        else -> println("Unknown: $lineName : $lineValue")
                    }
                }

                val endStyle = line.startsWith("</style>")
                if (endStyle) {
                    map[name] = Style(fontFamily, textSize, textStyle, lineHeight, fontWeight, textAllCaps)
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
        val fontFamily: String?,
        val textSize: Int,
        val textStyle: String?,
        val lineHeight: Int,
        val fontWeight: Int,
        val textAllCaps: Boolean = false,
        val letterSpacing: Float = 1f
    )

    data class Font(
        val size: Int,
        val weight: Int,
        val lineHeight: Int,
        val letterSpacing: Float
    )
}