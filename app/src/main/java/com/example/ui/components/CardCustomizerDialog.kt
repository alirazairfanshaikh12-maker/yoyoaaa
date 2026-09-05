package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardCustomizerDialog(
    initialSalutation: String,
    initialMessage: String,
    initialSender: String,
    onDismiss: () -> Unit,
    onSave: (salutation: String, message: String, sender: String) -> Unit,
    onResetTemplate: () -> Unit
) {
    var salutation by remember { mutableStateOf(initialSalutation) }
    var message by remember { mutableStateOf(initialMessage) }
    var sender by remember { mutableStateOf(initialSender) }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(28.dp),
        containerColor = ArtisticSurface,
        title = {
            Text(
                text = "Personalize Your Card",
                color = ArtisticTextPrimary,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Add your personal memories and heartfelt words for your teacher:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ArtisticTextSecondary
                )

                OutlinedTextField(
                    value = salutation,
                    onValueChange = { salutation = it },
                    label = { Text("Teacher Salutation") },
                    placeholder = { Text("e.g. To my mentors,") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("salutation_input")
                )

                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("Heartfelt Message") },
                    minLines = 5,
                    maxLines = 10,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("message_input")
                )

                OutlinedTextField(
                    value = sender,
                    onValueChange = { sender = it },
                    label = { Text("Your Name (Student)") },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null, tint = ArtisticPrimary)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("sender_name_input")
                )

                TextButton(
                    onClick = onResetTemplate,
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(16.dp), tint = ArtisticPrimary)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Reset to Template Message", color = ArtisticPrimary)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(salutation.trim(), message.trim(), sender.trim().ifEmpty { "Ali Raza" })
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ArtisticPrimary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("save_customization_button")
            ) {
                Text("Apply to Card")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = ArtisticTextSecondary)
            ) {
                Text("Cancel")
            }
        }
    )
}
