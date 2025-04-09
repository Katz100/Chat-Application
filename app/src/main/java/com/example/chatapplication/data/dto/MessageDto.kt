package com.example.chatapplication.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(

    @SerialName("id")
    val id: String,

    @SerialName("conversation_id")
    val conversationId: String,

    @SerialName("sender_id")
    val senderId: String,

    @SerialName("body")
    val body: String,

    @SerialName("read")
    val read: Boolean,

    @SerialName("created_at")
    val createdAt: String

){}
