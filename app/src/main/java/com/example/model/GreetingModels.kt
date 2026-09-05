package com.example.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.*

enum class TeacherRecipient(
    val id: String,
    val shortName: String,
    val formalSalutation: String,
    val dedication: String,
    val headlineName: String
) {
    BOTH(
        id = "both",
        shortName = "Nida & Asiya",
        formalSalutation = "To my mentors,",
        dedication = "Dedicated to Teacher Nida & Teacher Asiya",
        headlineName = "Nida &\nAsiya"
    ),
    NIDA(
        id = "nida",
        shortName = "Teacher Nida",
        formalSalutation = "To my mentor,",
        dedication = "Dedicated with gratitude to Teacher Nida",
        headlineName = "Teacher\nNida"
    ),
    ASIYA(
        id = "asiya",
        shortName = "Teacher Asiya",
        formalSalutation = "To my mentor,",
        dedication = "Dedicated with gratitude to Teacher Asiya",
        headlineName = "Teacher\nAsiya"
    )
}

enum class CardThemeStyle(
    val displayName: String,
    val backgroundGradient: List<Color>,
    val cardBackground: Color,
    val borderColor: Color,
    val accentColor: Color,
    val textColor: Color,
    val subtitleColor: Color,
    val sealColor: Color,
    val containerColor: Color = ArtisticPrimaryContainer,
    val onContainerColor: Color = ArtisticOnPrimaryContainer
) {
    ARTISTIC_FLAIR(
        displayName = "Artistic Flair",
        backgroundGradient = listOf(ArtisticBackground, Color(0xFFFFECE9)),
        cardBackground = ArtisticSurface,
        borderColor = ArtisticBorder,
        accentColor = ArtisticPrimary,
        textColor = ArtisticTextPrimary,
        subtitleColor = ArtisticTextSecondary,
        sealColor = ArtisticPrimary,
        containerColor = ArtisticPrimaryContainer,
        onContainerColor = ArtisticOnPrimaryContainer
    ),
    ROYAL_NAVY(
        displayName = "Royal & Gold",
        backgroundGradient = listOf(MidnightNavy, RoyalNavy),
        cardBackground = SoftParchment,
        borderColor = WarmGold,
        accentColor = DeepGold,
        textColor = WarmCharcoal,
        subtitleColor = MutedSlate,
        sealColor = DeepGold,
        containerColor = PaleGold,
        onContainerColor = RoyalNavy
    ),
    WARM_BURGUNDY(
        displayName = "Burgundy Blush",
        backgroundGradient = listOf(DeepBurgundy, Color(0xFF330812)),
        cardBackground = SoftBlush,
        borderColor = Color(0xFFC9848D),
        accentColor = RoseWine,
        textColor = Color(0xFF261215),
        subtitleColor = Color(0xFF6B454B),
        sealColor = RoseWine,
        containerColor = Color(0xFFFFDAD6),
        onContainerColor = Color(0xFF410002)
    ),
    EMERALD_WISDOM(
        displayName = "Sage & Emerald",
        backgroundGradient = listOf(ForestEmerald, Color(0xFF0E2211)),
        cardBackground = SoftSage,
        borderColor = Color(0xFF8BA88E),
        accentColor = SageGreen,
        textColor = Color(0xFF19261B),
        subtitleColor = Color(0xFF4A5C4C),
        sealColor = ForestEmerald,
        containerColor = Color(0xFFD6E8D8),
        onContainerColor = ForestEmerald
    )
}

data class GreetingTemplate(
    val id: String,
    val title: String,
    val tag: String,
    val messageText: String
)

object GreetingData {
    const val STUDENT_NAME = "Ali Raza"
    const val EVENT_DATE = "Happy Teacher's Day 2026"

    val templates = listOf(
        GreetingTemplate(
            id = "artistic_hand_mind_heart",
            title = "Touches a Heart",
            tag = "Artistic Flair",
            messageText = "A teacher takes a hand, opens a mind, and touches a heart. Thank you for your patience, guidance, and for lighting the path for me with unwavering mentorship and care."
        ),
        GreetingTemplate(
            id = "guidance_mentorship",
            title = "Guidance & Mentorship",
            tag = "Heartfelt",
            messageText = "Happy Teacher's Day! Today, I want to express my deepest gratitude for your unwavering guidance, wisdom, and mentorship. You didn't just teach lessons from textbooks—you nurtured my curiosity, believed in me when I hesitated, and guided my path with patience and kindness. Thank you for shaping not only my knowledge, but my character and aspirations."
        ),
        GreetingTemplate(
            id = "deep_gratitude",
            title = "Lifelong Gratitude",
            tag = "Inspirational",
            messageText = "On this auspicious Teacher's Day, I am reflecting on the immense impact you have had on my journey. A truly extraordinary mentor is rare, and I am blessed to have learned under your care. Your encouraging words, gentle corrections, and steadfast dedication will forever be the foundation of whatever success I achieve."
        ),
        GreetingTemplate(
            id = "wisdom_light",
            title = "Beacon of Wisdom",
            tag = "Respectful",
            messageText = "To the mentors who ignite hope, inspire dreams, and instill an enduring passion for learning. Thank you for your tireless patience, for opening doors to new horizons, and for always being a beacon of light and grace. Wishing you peace, fulfillment, and great joy today and always."
        )
    )
}
