package com.zyrdev.simplerest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.zyrdev.simplerest.model.User
import com.zyrdev.simplerest.ui.theme.PlaceholderColor
import com.zyrdev.simplerest.ui.theme.SimpleRestTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * MainActivity - Actividad principal de la aplicación
 * Contiene la UI principal que muestra la lista de usuarios
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleRestTheme {
                MyApp()
            }
        }
    }
}

/**
 * Composable principal que conecta con el ViewModel
 */
@Composable
fun MyApp(
    viewModel: UserViewModel = hiltViewModel()
) {
    val users by viewModel.users.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(initial = false)

    MyAppContent(
        onDeleteUser = { user -> viewModel.delectUser(user) },
        onAddUserClick = { viewModel.addUser() },
        users = users,
        isLoading = isLoading
    )
}

/**
 * Contenido principal de la aplicación - UI sin estado para facilitar previews
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppContent(
    onDeleteUser: (User) -> Unit = {},
    onAddUserClick: () -> Unit = {},
    users: List<User> = emptyList(),
    isLoading: Boolean = false
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Random Users",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                actions = {
                    // Botón para agregar nuevo usuario
                    IconButton(
                        onClick = onAddUserClick,
                        enabled = !isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Agregar Usuario",
                            tint = if (isLoading) Color.Gray else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Lista de usuarios
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Card de carga si está cargando
                if (isLoading) {
                    item {
                        LoadingCard()
                    }
                }

                // Mostrar mensaje si no hay usuarios
                if (users.isEmpty() && !isLoading) {
                    item {
                        EmptyStateCard(onAddUserClick = onAddUserClick)
                    }
                }

                // Lista de usuarios
                items(users, key = { it.id }) { user ->
                    UserCard(
                        user = user,
                        onDeleteClick = { onDeleteUser(user) }
                    )
                }
            }
        }
    }
}

/**
 * Card para mostrar el estado vacío cuando no hay usuarios
 */
@Composable
fun EmptyStateCard(onAddUserClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No hay usuarios",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Toca el botón + para agregar tu primer usuario",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onAddUserClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Agregar Usuario")
            }
        }
    }
}

/**
 * Card que muestra la información de un usuario
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(
    user: User,
    onDeleteClick: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
        onClick = { /* Aquí se podría navegar a detalles del usuario */ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Imagen del usuario
            AsyncImage(
                model = user.thumbnail,
                contentDescription = "Foto de ${user.name}",
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentScale = ContentScale.Crop
            )

            // Información del usuario
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                // Nombre completo
                Text(
                    text = "${user.name} ${user.lastName}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Ciudad
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = user.city,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Botón de eliminar
            IconButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar usuario",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    // Diálogo de confirmación para eliminar
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar Usuario") },
            text = { Text("¿Estás seguro de que deseas eliminar a ${user.name} ${user.lastName}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

/**
 * Card de carga con efecto shimmer
 */
@Composable
fun LoadingCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Círculo de imagen placeholder
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .placeholder(
                        visible = true,
                        color = PlaceholderColor,
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )

            // Información placeholder
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Nombre placeholder
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .placeholder(
                            visible = true,
                            color = PlaceholderColor,
                            highlight = PlaceholderHighlight.shimmer()
                        )
                )

                // Ciudad placeholder
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(15.dp)
                        .placeholder(
                            visible = true,
                            color = PlaceholderColor,
                            highlight = PlaceholderHighlight.shimmer()
                        )
                )
            }
        }
    }
}

// ============== PREVIEWS ==============

@Preview(showBackground = true, name = "Estado Vacío")
@Composable
fun EmptyStatePreview() {
    SimpleRestTheme {
        MyAppContent(
            users = emptyList(),
            isLoading = false
        )
    }
}

@Preview(showBackground = true, name = "Cargando")
@Composable
fun LoadingPreview() {
    SimpleRestTheme {
        MyAppContent(
            users = emptyList(),
            isLoading = true
        )
    }
}

@Preview(showBackground = true, name = "Con Usuarios")
@Composable
fun WithUsersPreview() {
    SimpleRestTheme {
        MyAppContent(
            users = listOf(
                User("John", "Doe", "New York", "https://randomuser.me/api/portraits/thumb/men/1.jpg", 1),
                User("Jane", "Smith", "Los Angeles", "https://randomuser.me/api/portraits/thumb/women/2.jpg", 2),
                User("Bob", "Johnson", "Chicago", "https://randomuser.me/api/portraits/thumb/men/3.jpg", 3)
            ),
            isLoading = false
        )
    }
}

