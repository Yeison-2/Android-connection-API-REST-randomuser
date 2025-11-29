package com.zyrdev.simplerest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyrdev.simplerest.datasource.RestDataSource
import com.zyrdev.simplerest.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl
) : ViewModel() {
    fun getUser() {
        Log.d("UserViewModel", "=== INICIANDO LLAMADA A API ===")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("UserViewModel", "Llamando a userRepositoryImpl.getNewUser()...")
                val user = userRepositoryImpl.getNewUser()
                Log.d("UserViewModel", "✅ Usuario obtenido exitosamente: $user")
                Log.d("UserViewModel", "Nombre: ${user.name}, Apellido: ${user.lastName}, Ciudad: ${user.city}")
            } catch (e: Exception) {
                Log.e("UserViewModel", "❌ ERROR al obtener usuario", e)
                Log.e("UserViewModel", "Mensaje: ${e.message}")
                Log.e("UserViewModel", "Causa: ${e.cause?.message}")
                e.printStackTrace()
            }
        }
    }
}