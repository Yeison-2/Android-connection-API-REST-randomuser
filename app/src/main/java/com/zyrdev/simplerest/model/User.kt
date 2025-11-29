package com.zyrdev.simplerest.model

import android.media.ThumbnailUtils

data class User (
    val name: String,
    val lastName: String,
    val city: String,
    val thumbnail: String,
    var id: Int = 0
){
}