package com.zyrdev.simplerest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding // Asegúrate de que este import esté
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import com.zyrdev.simplerest.model.User
import com.zyrdev.simplerest.ui.theme.SimpleRestTheme
import dagger.hilt.android.AndroidEntryPoint



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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel() // Hilt se queda aquí

) {
    val users by viewModel.users.observeAsState(arrayListOf())
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    MyAppContent(
        modifier = modifier,
        onAddUserClick = { viewModel.addUser() },
        users = users,
        isLoading = isLoading
    )
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppContent(
    modifier: Modifier = Modifier,
    onAddUserClick: () -> Unit,
    users: List<User>,
    isLoading: Boolean
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("API randomuser + Room") },
                actions = {
                    IconButton(onClick = onAddUserClick) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add User"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue, // Color de fondo de la barra
                    titleContentColor = Color.White, // Color del texto del título
                    actionIconContentColor = Color.White // Color de los iconos de acción
                )
            )
        }
    ) {  innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)){
            var itemCount = users.size
            if (isLoading) itemCount++
            items(count = itemCount){ index ->
                var auxIndex = index;
                if (isLoading) {
                    if (auxIndex == 0)
                        return@items LoadingCard()
                    auxIndex--
                }
            }
        }
    }
}

@Composable
fun LoadingCard(){
    Card (
        shape = RoundedCornerShape(8.dp),
        elevation = 1.dp,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .testTag("loadingCard")
    ){
        Row(modifier = Modifier.padding(8.dp)) {
            ImageLoading()
            Spacer()
            Column() {
                Spacer()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp)
                        .placeholder(
                            visible = true,
                            color = Color.Gray,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.White
                            )
                        )
                )
                Spacer()
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f) // Make the second line shorter
                        .height(15.dp)
                        .placeholder(
                            visible = true,
                            color = Color.Gray,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.White
                            )
                        )
                )
            }
        }
    }

}

@Composable
fun ImageLoading(){
    Box(
        modifier = Modifier
            .size(50.dp)
            .placeholder(
                visible = true, // El placeholder es visible
                color = Color.Gray, // Color de fondo del área de carga
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = Color.White,
                )
            )
    )
}

@Composable
fun Spacer(size: Int = 8) = Spacer(modifier = Modifier.padding(size.dp))


// 3. ACTUALIZA TU PREVIEW PARA USAR EL COMPOSABLE "SIN ESTADO"
@Preview(showBackground = true)
@Composable
fun MyAppPreview() { // Renombrado para claridad
    SimpleRestTheme {
        // Ahora previsualizas el Composable de UI, que no necesita Hilt.
        // Le pasas una acción vacía para el clic.
        MyAppContent(users = listOf(), isLoading = false, onAddUserClick = {})
    }
}
