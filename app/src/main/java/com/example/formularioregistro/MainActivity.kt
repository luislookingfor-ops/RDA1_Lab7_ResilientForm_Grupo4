package com.example.formularioregistro

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.formularioregistro.data.UserPreferences
import com.example.formularioregistro.ui.FormViewModel
import com.example.formularioregistro.ui.FormViewModelFactory
import androidx.compose.runtime.livedata.observeAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userPreferences = UserPreferences(applicationContext)

        setContent {
            val viewModel: FormViewModel = viewModel(
                factory = FormViewModelFactory(this@MainActivity, userPreferences)
            )

            ResilientFormScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun ResilientFormScreen(viewModel: FormViewModel) {
    val nameDisk by viewModel.nameFromDisk.observeAsState("")
    var nameInput by remember(nameDisk) { mutableStateOf(nameDisk) }

    LaunchedEffect(nameDisk) {
        if (nameInput.isEmpty() && nameDisk.isNotEmpty()) {
            nameInput = nameDisk
        }
    }

    BackHandler(enabled = nameInput.isNotEmpty()) {
        Log.d("NAV", "El usuario intentó retroceder con datos en el formulario")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            "Borrador de Perfil",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = nameInput,
            onValueChange = {
                nameInput = it
                viewModel.saveName(it)
            },
            label = { Text("Nombre (Persiste al cerrar app)") }
        )

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email (Persiste al rotar/muerte proceso)") }
        )
    }
}