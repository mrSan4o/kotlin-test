package com.san4o.test

import com.google.gson.Gson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi




fun main() {
//    val data: JsonData = Gson().fromJson<JsonData>("{}", JsonData::class.java)
    val moshi = Moshi.Builder().build()
    val jsonAdapter: JsonAdapter<JsonData> = moshi.adapter(JsonData::class.java)
    val data: JsonData? = jsonAdapter.fromJson("{\"name\": null}")
    println(data!!.name.text)
}

@JsonClass(generateAdapter = true)
data class JsonData(
        val name: Name
) {
    data class Name(val text: String)
}

