package ru.sportmaster.tools.read

import java.lang.StringBuilder

val componentsPath =
    "E:\\Development\\Projects\\AndroidProjects\\sportmaster\\flexo-components-android\\components\\src\\main\\res"
val samplePath =
    "E:\\Development\\Projects\\AndroidProjects\\sportmaster\\flexo-components-android\\sample\\src\\main\\res"
val appPath =
    "E:\\Development\\Projects\\AndroidProjects\\sportmaster\\mobexp\\mobexp-android-app\\app\\src\\main\\res"

fun main() {

    colorsReplacer(appPath)
}

fun fontsReplacer(projectResPath: String) {
    val replaceItems =
        StyleReader.readStyleXml("E:\\Development\\Projects\\IdeaProjects\\kotlin-maven-test\\src\\main\\kotlin\\ru\\sportmaster\\tools\\read\\fonts_0.0.4.xml")
            .map { color ->
                ReplaceItem(
                    type = ReplaceType.FONT,
                    old = "@style/" + color.name,
                    new = "@style/" + color.parent
                )
            }
    println("Replace items")
    replaceItems.forEach { println(it.old + " >> " + it.new) }
    println()


    StyleReplacer.replace(
        path = projectResPath,
        replaceItems = replaceItems
    )
}

fun colorsReplacer(projectResPath: String) {
    val replaceItems =
        StyleReader.read("E:\\Development\\Projects\\IdeaProjects\\kotlin-maven-test\\src\\main\\kotlin\\ru\\sportmaster\\tools\\read\\colors_0.0.4.xml")
            .filter { it.value.contains("@color") }
            .map { color ->
                ReplaceItem(
                    type = ReplaceType.COLOR,
                    old = "@color/" + color.name,
                    new = color.value
                )
            }
    println("Replace items")
    replaceItems.forEach { println(it.old + " >> " + it.new) }
    println()


    StyleReplacer.replace(
        path = projectResPath,
        replaceItems = replaceItems
    )
}

private fun spaceReplacer() {
    val replaceItems =
        StyleReader.read("E:\\Development\\Projects\\AndroidProjects\\sportmaster\\mobexp\\mobexp-android-app\\app\\src\\main\\res\\values\\dimens_flexo0.0.4.xml")
            .map { dimen ->

                ReplaceItem(
                    type = ReplaceType.DIMEN,
                    old = "@dimen/" + dimen.name,
                    new = dimen.value
                )
            }

    println("Replace items")
    replaceItems.forEach { println(it.old + " >> " + it.new) }

    StyleReplacer.replace(
        path = "E:\\Development\\Projects\\AndroidProjects\\sportmaster\\mobexp\\mobexp-android-app\\app\\src\\main\\res\\",
        replaceItems = replaceItems
    )
}

object StyleReplacer {
    fun replace(path: String, replaceItems: List<ReplaceItem>) {
        FileScanner.scan(path) { file ->

            var count = 0
            val content = file.readText().let { StringBuilder(it) }
            for (replaceItem in replaceItems) {
                val oldValue = replaceItem.old
                val newValue = replaceItem.new
                count += replace(content, oldValue.quotes(), newValue.quotes())
                count += replace(content, oldValue.tagValue(), newValue.tagValue())
            }

            file.writeText(content.toString())
            if (count > 0)
                println("${file.name} : $count replaces")
        }
    }

    private fun replace(builder: StringBuilder, oldValue: String, newValue: String): Int {
        var index = builder.indexOf(oldValue)
        var count = 0
        while (index >= 0) {
            builder.delete(index, index + oldValue.length)
            builder.insert(index, newValue)
            count++
            index = builder.indexOf(oldValue)
        }

        return count
    }
}

private fun String.quotes(): String {
    return "\"$this\""
}

private fun String.tagValue(): String {
    return ">$this</"
}

data class ReplaceItem(
    val type: ReplaceType,
    val old: String,
    val new: String,
)

enum class ReplaceType {
    COLOR,
    DIMEN,
    FONT,
}