// -------------------------------------------------------------
// MainActivity.kt – Parcial_uno
// -------------------------------------------------------------
package com.example.parcial_uno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

// ---------- CONSTANTES ----------
private const val STUDENT_1 = "Luis Boniche"
private const val STUDENT_2 = "Esteban "
private const val PARTIAL_NUMBER = 1

// ---------- LÓGICA ----------
private fun gradeFor(score: Int): Pair<String, String>? = when (score) {
    in 91..100 -> "A" to "Excelente"
    in 81..90  -> "B" to "Bueno"
    in 71..80  -> "C" to "Regular"
    in 61..70  -> "D" to "Más o menos regular"
    in 0..60   -> "F" to "No Aprobado, gracias por participar"
    else       -> null
}

// ---------- UI ----------
@Composable
private fun ParcialScreen() {
    var notaText by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(horizontal = 32.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Encabezado
            Text("Parcial #$PARTIAL_NUMBER", style = MaterialTheme.typography.headlineSmall)
            Text(STUDENT_1, fontSize = 16.sp)
            Text(STUDENT_2, fontSize = 16.sp)

            Spacer(Modifier.height(16.dp))

            Text("Ingrese la nota a validar:", style = MaterialTheme.typography.bodyMedium)

            // Caja de texto (sin restricciones de teclado)
            OutlinedTextField(
                value = notaText,
                onValueChange = { notaText = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Botón VALIDAR
            Button(
                onClick = {
                    val score = notaText.toIntOrNull()
                    val msg = when {
                        score == null -> "Por favor ingrese un número válido."
                        else -> gradeFor(score)?.let { (letter, desc) ->
                            "Calificación: $letter ($desc)"
                        } ?: "La nota debe estar entre 0 y 100."
                    }
                    scope.launch { snackbarHostState.showSnackbar(msg) }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("VALIDAR")
            }
        }
    }
}

// ---------- PUNTO DE ENTRADA ----------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MaterialTheme { ParcialScreen() } }
    }
}

// ---------- PREVIEW ----------
@Preview(showBackground = true)
@Composable
private fun ParcialPreview() {
    MaterialTheme { ParcialScreen() }
}
