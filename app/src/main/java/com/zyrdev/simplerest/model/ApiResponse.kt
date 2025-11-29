package com.zyrdev.simplerest.model

data class ApiResponse (
    val results: List<Result> = emptyList(),
) {
}

data class Result(
    val name: UserName?,
    val location: UserLocation?,
    val picture: UserPicture?
)