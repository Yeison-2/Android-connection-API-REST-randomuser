package com.zyrdev.simplerest.model

data class UserPicture(
    val large: String? = null,
    val medium: String? = null,
    val thumbnail: String? = null
) {
    // Propiedad de conveniencia para obtener la imagen disponible
    val thumbnails: String
        get() = thumbnail ?: medium ?: large ?: ""
}
