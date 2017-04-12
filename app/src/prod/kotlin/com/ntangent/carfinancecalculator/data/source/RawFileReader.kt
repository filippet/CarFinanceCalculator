package com.ntangent.carfinancecalculator.data.source

import android.content.Context
import android.support.annotation.IdRes
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter

class RawFileReader(
        private val context: Context,
        @IdRes private val resourceFileId: Int
): JsonFileReader {

    override fun readJsonFile(): String {
        val inputStream = context.getResources().openRawResource(resourceFileId)
        val writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var n: Int = reader.read(buffer)
            while (n != -1) {
                writer.write(buffer, 0, n)
                n = reader.read(buffer)
            }
        } finally {
            inputStream.close()
        }
        val jsonString = writer.toString()
        return jsonString
    }

}