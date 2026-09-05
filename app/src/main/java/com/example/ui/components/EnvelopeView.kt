package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Drafts
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CardThemeStyle
import com.example.model.TeacherRecipient
import com.example.ui.theme.ArtisticDarkAccent
import com.example.ui.theme.ArtisticMuted

@Composable
fun EnvelopeView(
    recipient: TeacherRecipient,
    senderName: String,
    cardTheme: CardThemeStyle,
    onOpenCard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onOpenCard() }
            .shadow(elevation = 14.dp, shape = RoundedCornerShape(32.dp), spotColor = cardTheme.containerColor)
            .testTag("envelope_container"),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = cardTheme.cardBackground),
        border = BorderStroke(1.5.dp, cardTheme.borderColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Postage stamp & postmark row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    // Special Delivery Tag
                    Surface(
                        color = cardTheme.containerColor,
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, cardTheme.borderColor)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = cardTheme.onContainerColor,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "TEACHER'S DAY 2026",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = cardTheme.onContainerColor,
                                letterSpacing = 0.8.sp
                            )
                        }
                    }

                    // Stamp
                    Surface(
                        modifier = Modifier
                            .size(width = 48.dp, height = 56.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = cardTheme.containerColor.copy(alpha = 0.6f),
                        border = BorderStroke(1.dp, cardTheme.borderColor)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = null,
                                tint = cardTheme.accentColor,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "SEPT 5",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = cardTheme.accentColor
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(26.dp))

                // Recipient address style
                Column(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "To my mentors,",
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily.Serif,
                        color = cardTheme.accentColor
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = recipient.headlineName.replace("\n", " "),
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = cardTheme.textColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "With heartfelt appreciation for your mentorship & care",
                        fontSize = 13.sp,
                        color = cardTheme.subtitleColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "WITH GRATITUDE,",
                        fontSize = 10.sp,
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.Medium,
                        color = ArtisticMuted
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = senderName,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                        color = cardTheme.textColor
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Wax Seal Button (Crimson #BA1A1A)
                Surface(
                    modifier = Modifier
                        .scale(pulseScale)
                        .size(74.dp)
                        .clickable { onOpenCard() }
                        .shadow(8.dp, CircleShape, spotColor = cardTheme.sealColor)
                        .testTag("open_wax_seal_button"),
                    shape = CircleShape,
                    color = cardTheme.sealColor
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Drafts,
                                contentDescription = "Open Card",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "OPEN",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Tap seal to open card",
                    fontSize = 12.sp,
                    color = cardTheme.subtitleColor,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}
