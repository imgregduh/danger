package com.doubleu.danger.entities

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.*


@Document(collection = "profiles")
data class ProfileEntity(
        @JsonSerialize(using = ToStringSerializer::class)
        var _id: ObjectId? = null,
        var email: String,
        var firstName: String,
        var lastName: String,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "GMT")
        var createdAt: Date = Date.from(Instant.now())
)