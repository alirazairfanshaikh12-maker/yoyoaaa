package com.example.ui

import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.CardThemeStyle
import com.example.model.GreetingData
import com.example.model.TeacherRecipient
import com.example.ui.components.*
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetingScreen(
    viewModel: GreetingViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            // Artistic Flair Header matching the design
            Surface(
                color = ArtisticBackground,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Favorite badge container
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(ArtisticPrimaryContainer)
                                .clickable {
                                    viewModel.triggerCelebration()
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Sending appreciation to teachers! ✨")
                                    }
                                }
                                .testTag("celebrate_icon_button"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Gratitude Favorite",
                                tint = ArtisticOnPrimaryContainer,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "Teacher's Day",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = ArtisticTextPrimary
                            )
                            Text(
                                text = "SPECIAL EDITION",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = ArtisticMuted,
                                letterSpacing = 1.2.sp
                            )
                        }
                    }

                    // Share Button with subtle outlined border
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(1.dp, ArtisticOutline, CircleShape)
                            .clickable {
                                val shareText = uiState.generateFullShareText()
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(sendIntent, "Share Teacher's Day Card"))
                            }
                            .testTag("top_share_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = ArtisticTextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = uiState.selectedTheme.backgroundGradient
                    )
                )
        ) {
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Teacher Recipient Selector Tabs
                TeacherSelectorBar(
                    selectedRecipient = uiState.recipient,
                    onSelect = { viewModel.selectRecipient(it) },
                    theme = uiState.selectedTheme
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Card Presentation (Envelope vs Unfolded Card)
                AnimatedContent(
                    targetState = uiState.isCardOpened,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(400)) + scaleIn(initialScale = 0.95f) togetherWith
                                fadeOut(animationSpec = tween(300))
                    },
                    label = "card_toggle"
                ) { isOpened ->
                    if (isOpened) {
                        GreetingCardView(
                            recipient = uiState.recipient,
                            customSalutation = uiState.activeSalutation,
                            messageBody = uiState.activeMessage,
                            senderName = uiState.senderName,
                            cardTheme = uiState.selectedTheme
                        )
                    } else {
                        EnvelopeView(
                            recipient = uiState.recipient,
                            senderName = uiState.senderName,
                            cardTheme = uiState.selectedTheme,
                            onOpenCard = { viewModel.openCard() }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Primary Artistic Flair Action Buttons (Customize & Send Card)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Customize Button
                    Button(
                        onClick = { viewModel.showCustomizer(true) },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .testTag("action_personalize"),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ArtisticPrimaryContainer,
                            contentColor = ArtisticOnPrimaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Customize",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }

                    // Send Card Button
                    Button(
                        onClick = {
                            val shareText = uiState.generateFullShareText()
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, shareText)
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "Send Card to Teachers"))
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = ArtisticPrimary)
                            .testTag("action_share_card"),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ArtisticPrimary,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Send Card",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Secondary Tool Strip (Envelope Toggle, Send Love, Copy Text)
                Surface(
                    color = ArtisticSurface.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, ArtisticBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Envelope / Card Toggle
                        TextButton(
                            onClick = { viewModel.toggleCardOpen() },
                            modifier = Modifier.testTag("action_toggle_envelope")
                        ) {
                            Icon(
                                imageVector = if (uiState.isCardOpened) Icons.Default.Mail else Icons.Default.Drafts,
                                contentDescription = null,
                                tint = ArtisticTextSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (uiState.isCardOpened) "View Envelope" else "Unfold Card",
                                color = ArtisticTextSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Sparkles & Gratitude
                        IconButton(
                            onClick = {
                                viewModel.triggerCelebration()
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Gratitude and sparkles sent! 💖")
                                }
                            },
                            modifier = Modifier.testTag("action_express_gratitude")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Express Gratitude",
                                tint = ArtisticPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Copy Text
                        TextButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(uiState.generateFullShareText()))
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Card message copied! Ready to paste.")
                                }
                            },
                            modifier = Modifier.testTag("action_copy_message")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = null,
                                tint = ArtisticTextSecondary,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Copy Text",
                                color = ArtisticTextSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Template Carousel / Picker
                TemplatesSection(
                    selectedIndex = uiState.selectedTemplateIndex,
                    onSelect = { viewModel.selectTemplate(it) },
                    theme = uiState.selectedTheme
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Theme Style Switcher
                CardThemeSelector(
                    selectedTheme = uiState.selectedTheme,
                    onSelect = { viewModel.selectTheme(it) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Student Identity Card Credit
                Surface(
                    color = ArtisticSurface,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, ArtisticBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "CARD PREPARED BY",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 1.sp,
                                color = ArtisticMuted
                            )
                            Text(
                                text = uiState.senderName,
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                color = ArtisticTextPrimary
                            )
                        }

                        Button(
                            onClick = { viewModel.showCustomizer(true) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ArtisticPrimaryContainer,
                                contentColor = ArtisticOnPrimaryContainer
                            ),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Edit Text", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Tagline matching Artistic Flair Design
                Text(
                    text = "DESIGNED FOR ALI RAZA • EXCLUSIVE TEMPLATE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.2.sp,
                    color = ArtisticMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))
            }

            // Floating celebration particle effects
            CelebrationParticles(
                trigger = uiState.celebrationTrigger,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    // Customizer dialog
    if (uiState.showCustomizerDialog) {
        CardCustomizerDialog(
            initialSalutation = uiState.activeSalutation,
            initialMessage = uiState.activeMessage,
            initialSender = uiState.senderName,
            onDismiss = { viewModel.showCustomizer(false) },
            onSave = { salutation, message, sender ->
                viewModel.updateCustomContent(salutation, message, sender)
            },
            onResetTemplate = {
                viewModel.resetToTemplate()
            }
        )
    }
}

@Composable
fun TeacherSelectorBar(
    selectedRecipient: TeacherRecipient,
    onSelect: (TeacherRecipient) -> Unit,
    theme: CardThemeStyle
) {
    Surface(
        color = ArtisticSurface,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, ArtisticBorder),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TeacherRecipient.values().forEach { recipient ->
                val isSelected = recipient == selectedRecipient
                Surface(
                    onClick = { onSelect(recipient) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) ArtisticPrimary else Color.Transparent,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("select_teacher_${recipient.id}")
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = recipient.shortName,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp,
                            color = if (isSelected) Color.White else ArtisticTextSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TemplatesSection(
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    theme: CardThemeStyle
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FormatQuote,
                contentDescription = null,
                tint = ArtisticPrimary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Heartfelt Message Templates",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = ArtisticTextPrimary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GreetingData.templates.forEachIndexed { index, template ->
                val isSelected = index == selectedIndex
                FilterChip(
                    selected = isSelected,
                    onClick = { onSelect(index) },
                    label = {
                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text(
                                text = template.title,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = if (isSelected) ArtisticOnPrimaryContainer else ArtisticTextPrimary
                            )
                            Text(
                                text = template.tag,
                                fontSize = 10.sp,
                                color = if (isSelected) ArtisticPrimary else ArtisticMuted
                            )
                        }
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = ArtisticSurface,
                        labelColor = ArtisticTextPrimary,
                        selectedContainerColor = ArtisticPrimaryContainer,
                        selectedLabelColor = ArtisticOnPrimaryContainer
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = ArtisticBorder,
                        selectedBorderColor = ArtisticPrimary,
                        enabled = true,
                        selected = isSelected
                    ),
                    modifier = Modifier.testTag("template_chip_$index")
                )
            }
        }
    }
}

@Composable
fun CardThemeSelector(
    selectedTheme: CardThemeStyle,
    onSelect: (CardThemeStyle) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Palette,
                contentDescription = null,
                tint = ArtisticPrimary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Card Theme Aesthetic",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = ArtisticTextPrimary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardThemeStyle.values().forEach { theme ->
                val isSelected = theme == selectedTheme
                Surface(
                    onClick = { onSelect(theme) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) ArtisticPrimaryContainer else ArtisticSurface,
                    border = BorderStroke(
                        width = if (isSelected) 1.5.dp else 1.dp,
                        color = if (isSelected) ArtisticPrimary else ArtisticBorder
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(theme.sealColor)
                                .border(1.dp, ArtisticBorder, CircleShape)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = theme.displayName,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) ArtisticOnPrimaryContainer else ArtisticTextSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
