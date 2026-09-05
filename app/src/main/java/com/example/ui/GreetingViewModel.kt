package com.example.ui

import androidx.lifecycle.ViewModel
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GreetingUiState(
    val recipient: TeacherRecipient = TeacherRecipient.BOTH,
    val selectedTemplateIndex: Int = 0,
    val selectedTheme: CardThemeStyle = CardThemeStyle.ARTISTIC_FLAIR,
    val isCardOpened: Boolean = true,
    val customSalutation: String = "",
    val customMessage: String = "",
    val senderName: String = GreetingData.STUDENT_NAME,
    val celebrationTrigger: Long = 0L,
    val showCustomizerDialog: Boolean = false
) {
    val activeSalutation: String
        get() = if (customSalutation.isNotBlank()) customSalutation else recipient.formalSalutation

    val activeMessage: String
        get() = if (customMessage.isNotBlank()) customMessage else GreetingData.templates[selectedTemplateIndex].messageText

    fun generateFullShareText(): String {
        return buildString {
            append("💐 HAPPY TEACHER'S DAY 💐\n\n")
            append(activeSalutation)
            append("\n\n")
            append(activeMessage)
            append("\n\n")
            append("With deepest gratitude and profound respect,\n")
            append("Your grateful student,\n")
            append(senderName)
            append("\n\n")
            append("Teacher's Day • September 5")
        }
    }
}

class GreetingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GreetingUiState())
    val uiState: StateFlow<GreetingUiState> = _uiState.asStateFlow()

    fun selectRecipient(recipient: TeacherRecipient) {
        _uiState.update { current ->
            current.copy(
                recipient = recipient,
                // clear custom salutation so default formalSalutation for new recipient is used
                customSalutation = ""
            )
        }
    }

    fun selectTemplate(index: Int) {
        if (index in GreetingData.templates.indices) {
            _uiState.update { current ->
                current.copy(
                    selectedTemplateIndex = index,
                    customMessage = ""
                )
            }
        }
    }

    fun selectTheme(theme: CardThemeStyle) {
        _uiState.update { it.copy(selectedTheme = theme) }
    }

    fun toggleCardOpen() {
        _uiState.update { it.copy(isCardOpened = !it.isCardOpened) }
    }

    fun openCard() {
        _uiState.update { it.copy(isCardOpened = true) }
    }

    fun triggerCelebration() {
        _uiState.update { it.copy(celebrationTrigger = System.currentTimeMillis()) }
    }

    fun showCustomizer(show: Boolean) {
        _uiState.update { it.copy(showCustomizerDialog = show) }
    }

    fun updateCustomContent(salutation: String, message: String, sender: String) {
        _uiState.update { current ->
            current.copy(
                customSalutation = salutation,
                customMessage = message,
                senderName = sender.ifBlank { GreetingData.STUDENT_NAME }
            )
        }
    }

    fun resetToTemplate() {
        _uiState.update { current ->
            current.copy(
                customSalutation = "",
                customMessage = "",
                senderName = GreetingData.STUDENT_NAME
            )
        }
    }
}
