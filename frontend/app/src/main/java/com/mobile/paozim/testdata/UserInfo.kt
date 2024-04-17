package com.mobile.paozim.testdata

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("id")
    var id: Int,

    @SerializedName("nome")
    var nome: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("telefone")
    var telefone: String,

    @SerializedName("imageURL")
    var imageURL: String
)

//  ######### TESTE COM TUTORIAL #########
//data class UserInfo(
//    @SerializedName("userId")
//    var userId: Int = 0,
//
//    @SerializedName("id")
//    var id: Int = 0,
//
//    @SerializedName("title")
//    var title: String = "",
//
//    @SerializedName("body")
//    var body: String = ""
//)
//  ######### TESTE COM TUTORIAL #########
