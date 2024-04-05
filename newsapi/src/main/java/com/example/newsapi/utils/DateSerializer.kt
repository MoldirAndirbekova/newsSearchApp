package com.example.newsapi.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal object DateSerializer: KSerializer<Date> {
    override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    private val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    override fun serialize(encoder: Encoder, value: Date) = encoder.encodeString(formatter.format(value))

    override fun deserialize(decoder: Decoder): Date = formatter.parse(decoder.decodeString())
}