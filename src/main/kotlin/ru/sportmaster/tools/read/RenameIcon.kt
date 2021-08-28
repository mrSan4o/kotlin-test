package ru.sportmaster.tools.read

import java.io.File

fun main() {
    RenameIcon.rename(
        squarePath = "D:\\FROM INET\\@Opera\\Forwarder.Android",
        roundPath = "D:\\FROM INET\\@Opera\\Forwarder.Android (1)",
        targetPath = "D:\\test\\mobexp_icons"
    )
}

object RenameIcon {
    private const val iconFileName = "ic_launcher"
    private val excludeEnvs = emptyList<String>()

    fun rename(squarePath: String, roundPath: String, targetPath: String) {
        moveAndRename(squarePath, targetPath)
        moveAndRename(roundPath, targetPath, iconFileName + "_round")
    }

    private fun moveAndRename(squarePath: String, targetPath: String, targetFileName: String = iconFileName) {
        FileScanner.scan(squarePath) { file ->
            val fileName = file.name
            if (!fileName.startsWith(iconFileName)) {
                return@scan
            }
            val extension = file.extension

            val env = fileName
                .substringBeforeLast(".")
                .substringAfterLast("_").lowercase()
                .let { e ->
                    if (e.equals("app", true)) {
                        "prod"
                    } else {
                        e
                    }
                }
            if (excludeEnvs.contains(env)) {
                println("$file skiped")
                return@scan
            }

            val screenType = file.parentFile.name

            val targetFolder = File(targetPath, env + File.separator + "mipmap-" + screenType)
            if (!targetFolder.exists()) {
                targetFolder.makeDirs()
            }
            val targetName = "$targetFileName.$extension"

            println("copy $file, to $targetFolder $targetName")

            val target = File(targetFolder, targetName)
            file.copyTo(target, true)
        }
    }
}

