package ru.sportmaster.tools.read

class TestCompanionObject private constructor() {

    companion object {
        @JvmStatic
        val number = 1
    }
}

object TestObject {

    @JvmStatic
    val number = 1
}
