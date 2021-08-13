package ru.sportmaster.tools.read

import java.io.File

fun main3() {
    val style =
        "E:\\Development\\Projects\\AndroidProjects\\sportmaster\\flexo-components-android\\core\\src\\main\\res\\values\\fonts_0.0.4.xml"

    StyleReader.read("E:\\Development\\Projects\\AndroidProjects\\sportmaster\\mobexp\\mobexp-android-app\\app\\src\\main\\res\\values\\dimens_flexo0.0.4.xml")
        .forEach { println(it) }

    StyleReader.readStyleXml(style)
        .forEach { println(it) }
}

fun main1() {
    val path =
        "E:\\Development\\Projects\\AndroidProjects\\sportmaster\\flexo-components-android\\core\\src\\main\\res\\values\\spaces_const.xml"

    val items = StyleReader.read(path)

    items.forEach { item ->
        val value = item.value.replace("dp", "")
        println("<dimen name=\"space$value\">@dimen/${item.name}</dimen>")
    }
}
fun main() {
    val path =
        "E:\\Development\\Projects\\AndroidProjects\\sportmaster\\flexo-components-android\\core\\src\\main\\res\\values\\radiuses_const.xml"

    val items = StyleReader.read(path)

    items.forEach { item ->
        val value = item.value.replace("dp", "")
        println("<dimen name=\"radius$value\">@dimen/${item.name}</dimen>")
    }
}

object StyleReader {

    fun readStyleXml(filePath: String): List<StyleItem> {
        return File(filePath).readLines()
            .map { it.trim() }
            .mapNotNull { line ->
                if (line.startsWith("<style")) {
                    val name = getName(line)
                    val parent = line.substringAfter("parent=\"").substringBefore("\"")
                    StyleItem(
                        name = name,
                        parent = parent
                    )
                } else {
                    null
                }
            }
    }

    fun read(filePath: String): List<ResourceItem> {

        return File(filePath).readLines()
            .asSequence()
            .map { it.trim() }
            .mapNotNull { line ->
                if (line.isEmpty() || line.contains("resources") || line.contains("?xml version=\"1.0\" encoding=\"utf-8\"?")) {
                    return@mapNotNull null
                }
                when {
                    line.startsWith("<dimen") -> {
                        val (name, value) = getNameValue(line)
                        ResourceItem(
                            type = StyleType.DIMEN,
                            name = name,
                            value = value

                        )
                    }
                    line.startsWith("<color") -> {
                        val (name, value) = getNameValue(line)
                        ResourceItem(
                            type = StyleType.COLOR,
                            name = name,
                            value = value

                        )
                    }
                    else -> {
                        println("Unknown line: $line")
                        null
                    }
                }
            }
            .toList()
    }

    private fun getNameValue(line: String): Pair<String, String> {
        val name = getName(line)
        val value = line.substringAfter(">").substringBefore("</")
        return Pair(name, value)
    }

    private fun getName(line: String) = line.substringAfter("name=\"").substringBefore("\"")
}

data class StyleItem(
    val name: String,
    val parent: String
)

data class ResourceItem(
    val type: StyleType,
    val name: String,
    val value: String
)

enum class StyleType {
    COLOR,
    DIMEN,
}