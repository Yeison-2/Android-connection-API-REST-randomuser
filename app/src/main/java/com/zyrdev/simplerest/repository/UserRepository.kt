package com.zyrdev.simplerest.repository

import com.zyrdev.simplerest.datasource.RestDataSource
import com.zyrdev.simplerest.model.User
import javax.inject.Inject

interface UserRepository {
    suspend fun getNewUser(): User
}

class UserRepositoryImpl @Inject constructor(
    private val dataSource: RestDataSource
) : UserRepository {

    override suspend fun getNewUser(): User {
        android.util.Log.d("UserRepository", "Llamando a dataSource.getUser()...")
        val response = dataSource.getUser()
        android.util.Log.d("UserRepository", "Respuesta recibida. Results count: ${response.results.size}")
        
        if (response.results.isEmpty()) {
            android.util.Log.e("UserRepository", "La respuesta está vacía")
            throw IllegalStateException("No se encontraron usuarios en la respuesta")
        }
        
        val result = response.results[0]
        android.util.Log.d("UserRepository", "Result obtenido. Name: ${result.name}, Location: ${result.location}, Picture: ${result.picture}")
        
        val name = result.name ?: throw IllegalStateException("El nombre del usuario es nulo")
        val location = result.location ?: throw IllegalStateException("La ubicación del usuario es nula")
        val picture = result.picture ?: throw IllegalStateException("La imagen del usuario es nula")
        
        val firstName = name.first ?: throw IllegalStateException("El campo 'first' del nombre es nulo")
        val lastName = name.last ?: throw IllegalStateException("El campo 'last' del nombre es nulo")
        val city = location.city ?: throw IllegalStateException("El campo 'city' de la ubicación es nulo")
        
        android.util.Log.d("UserRepository", "Datos extraídos - First: $firstName, Last: $lastName, City: $city")
        
        return User(
            name = firstName,
            lastName = lastName,
            city = city,
            thumbnail = picture.thumbnails
        )
    }
}
