package com.mobile.paozim.classes

import kotlinx.serialization.Serializable

@Serializable
class Rating (
    var id: String? = null,
    var productID: String,
    var userID: String,
    var rating: Int,
    var comment: String,
    var date: String
)
