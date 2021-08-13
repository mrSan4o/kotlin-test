package ru.sportmaster.tools.loc

import java.io.File
val hhPath = "/Users/aleknazarov/StudioProjects/sportmaster/hh-sportmaster"
val kbPath = "/Users/aleknazarov/StudioProjects/sportmaster/kb-sm4-main-android"
val projectBase = "/Users/aleknazarov/StudioProjects/sportmaster/"
val hhModules = listOf(
    "hh-sportmaster",
    "hh-sportmaster-catalog",
    "hh-sportmaster-common-lib",
    "hh-sportmaster-subfeature-image-picker",
)
val kbModules = listOf(
    "kb-sm-service-catalog-android",
    "kb-sm4-main-android",
    "kb-sportmaster-sdk",
)
fun main() {
    printSubPorjects(hhModules)
    println("++++++++++++++++")
    printSubPorjects(kbModules)
}

private fun printSubPorjects(subProjects: List<String>) {
    subProjects.forEach { module ->
        val moduleStatMap = LineOfCodeReader.readProject("$projectBase/$module")

        println("Project: $module")
        printModuleInfo(moduleStatMap)
        println()
        println()
    }
}

fun main3() {

    val moduleStatMap = LineOfCodeReader.readProject(kbPath)

    moduleStatMap.entries.forEach { entry ->
        println(":" + entry.key)

        // entry.value.forEach { stat ->
        //     println(stat.file.name + " : " + stat.lineCodes)
        // }
        // println("-------------------------------------")
        val filesData = entry.value
        val sum = filesData.sumBy { it.lineCodes }
        println("Total lines: $sum")
        println("Total files: ${filesData.size}")
        val countUseCase = filesData.count { it.fileNameNoExt.endsWith("UseCase") }
        println("Usecase: ${countUseCase}")
//        entry.value.minLinesFile().printMinLinesFile()
//        entry.value.maxLinesFile().printMaxLinesFile()
        println()
    }
    println("++++++++++++++++++++++++++++++++++++++++")
    printModuleInfo(moduleStatMap)
    println()
    println()
    println()
    val moduleStatMapZeroLines = moduleStatMap.entries.associateBy(
        { it.key },
        { it.value.filter { f -> f.lineCodes == 0 } }
    )
        .filter { (module, files) -> files.isNotEmpty() }
    if (moduleStatMapZeroLines.isNotEmpty()) {
        println(">>>> Empty files:")
        moduleStatMapZeroLines
            .forEach { (module, files) ->
                println(":$module")
                files.forEach { println(it.fileName) }
            }
    }
}

private fun printModuleInfo(moduleStatMap: Map<String, List<LocStatFile>>) {
    val filesCount = moduleStatMap.entries.sumBy { it.value.size }
    println("Total files: $filesCount")
//    moduleStatMap.flatMap { it.value }.minLinesFile(0).printMinLinesFile()
//    moduleStatMap.flatMap { it.value }.maxLinesFile().printMaxLinesFile()
    println("modules : ${moduleStatMap.size}")
    val allFileDataList = moduleStatMap.flatMap { it.value }
    val totalCountUseCase = allFileDataList.count { it.fileNameNoExt.endsWith("UseCase") }
    println("Usecase: ${totalCountUseCase}")
    val activityTotalCount = allFileDataList.count { it.fileNameNoExt.endsWith("Activity") }
    println("Activity: ${activityTotalCount}")
    val fragmentTotalCount = allFileDataList.count { it.fileNameNoExt.endsWith("Fragment") }
    println("Fragment: ${fragmentTotalCount}")
}

private fun LocStatFile?.printMaxLinesFile() {
    println("Max lines file: ${this?.printString()}")
}

private fun LocStatFile?.printMinLinesFile() {
    println("Min lines file: ${this?.printString()}")
}

private fun LocStatFile.printString(): String {
    return "${file.name} : $lineCodes"
}

fun List<LocStatFile>.maxLinesFile(): LocStatFile? {
    return this.maxByOrNull { it.lineCodes }
}

fun List<LocStatFile>.minLinesFile(moreThan: Int? = null): LocStatFile? {
    if (moreThan != null) {
        return this.filter { it.lineCodes > moreThan }.minByOrNull { it.lineCodes }
    }
    return this.minByOrNull { it.lineCodes }
}

object LineOfCodeReader {

    fun readProject(path: String): Map<String, List<LocStatFile>> {
        val file = File(path)

        val foundModules = findModules(file)

        val statMap: Map<String, List<LocStatFile>> = foundModules.associateBy(
            { it.name },
            { statsIn(it.srcDir) }
        )

        return statMap
    }

    private fun statsIn(srcDir: File): List<LocStatFile> {
        val statFiles = ArrayList<LocStatFile>()
        findStatsIn(srcDir, statFiles)

        return statFiles
    }

    private fun findStatsIn(file: File, statFiles: ArrayList<LocStatFile>) {
        if (file.isDirectory) {
            file.listFiles().orEmpty()
                .forEach { findStatsIn(it, statFiles) }
        } else {
            if (file.name.endsWith(".kt")) {
                val count = file.readLines()
                    .asSequence()
                    .filter { it.isNotBlank() && !it.startsWith("package") && !it.startsWith("import") }
                    .count()

                statFiles.add(LocStatFile(file, count))
            }
        }
    }

    private fun findModules(file: File): List<Module> {
        val foundModules = ArrayList<Module>()
        file.listFiles().orEmpty()
            .forEach { findModules(it, foundModules) }

        return foundModules
    }

    private fun findModules(file: File, foundModules: MutableList<Module>) {
        if (file.isDirectory) {
            val files = file.listFiles().orEmpty()
            if (files.isNotEmpty()) {

                if (files.any { it.name == "build.gradle" }) {
                    val srcDir = file.ls("src/main/java")
                    if (srcDir != null)
                        foundModules.add(
                            Module(
                                name = file.name,
                                srcDir = srcDir
                            )
                        )
                } else {
                    files.forEach { findModules(it, foundModules) }
                }
            }
        }
    }

}

private fun File.ls(path: String): File? {
    val file = File(this.absolutePath + File.separator + path)
    return if (file.isDirectory && file.exists()) {
        file
    } else {
        null
    }
}

data class Module(
    val name: String,
    val srcDir: File
)

data class LocStatFile(
    val file: File,
    val lineCodes: Int
)

val LocStatFile.fileNameNoExt: String
    get() = this.file.name.substringBefore(".")
val LocStatFile.fileName: String
    get() = this.file.name