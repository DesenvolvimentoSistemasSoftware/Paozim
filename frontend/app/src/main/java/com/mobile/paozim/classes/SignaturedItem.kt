package com.mobile.paozim.classes

import kotlinx.serialization.Serializable

@Serializable
class SignaturedItem (
    val id: Int,
    val itemId: Int,
    val userEmail: String,
    val frequency: String // "Diariamente", "Semanalmente", "Mensalmente"
)
