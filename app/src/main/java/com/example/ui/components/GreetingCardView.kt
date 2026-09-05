package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.CardThemeStyle
import com.example.model.GreetingData
import com.example.model.TeacherRecipient
import com.example.ui.theme.*

@Composable
fun GreetingCardView(
    recipient: TeacherRecipient,
    customSalutation: String,
    messageBody: String,
    senderName: String,
    cardTheme: CardThemeStyle,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(36.dp),
                spotColor = cardTheme.containerColor
            )
            .testTag("greeting_card_container"),
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(containerColor = cardTheme.cardBackground),
        border = BorderStroke(1.dp, cardTheme.borderColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(36.dp))
        ) {
            // Atmospheric subtle glow orbs (Artistic Flair)
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .offset(x = 60.dp, y = (-60).dp)
                    .align(Alignment.TopEnd)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                cardTheme.containerColor.copy(alpha = 0.5f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )

            Box(
                modifier = Modifier
                    .size(180.dp)
                    .offset(x = (-70).dp, y = 70.dp)
                    .align(Alignment.BottomStart)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                cardTheme.containerColor.copy(alpha = 0.4f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Header badge row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = cardTheme.containerColor,
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, cardTheme.borderColor)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = cardTheme.onContainerColor,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "TEACHER'S DAY",
                                color = cardTheme.onContainerColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Text(
                        text = "SPECIAL EDITION",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ArtisticMuted,
                        letterSpacing = 1.2.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Hero Artwork Frame
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(155.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .border(BorderStroke(1.dp, cardTheme.borderColor), RoundedCornerShape(24.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_teachers_day_card),
                        contentDescription = "Teacher's Day Artwork",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Subtle bottom gradient
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f))
                                )
                            )
                    )

                    // Dedication tag inside artwork
                    Surface(
                        color = Color.Black.copy(alpha = 0.65f),
                        shape = RoundedCornerShape(topStart = 12.dp, bottomEnd = 14.dp),
                        modifier = Modifier.align(Alignment.BottomStart)
                    ) {
                        Text(
                            text = recipient.dedication,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Salutation
                Text(
                    text = customSalutation.ifBlank { recipient.formalSalutation },
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    fontSize = 19.sp,
                    color = cardTheme.accentColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Recipient Name in bold display serif
                Text(
                    text = recipient.headlineName,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    lineHeight = 36.sp,
                    color = cardTheme.textColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Artistic Accent Pill Divider
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(cardTheme.accentColor)
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Heartfelt Mentorship Quote & Message
                Text(
                    text = messageBody,
                    fontFamily = FontFamily.Serif,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = cardTheme.subtitleColor,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Date Note
                Text(
                    text = GreetingData.EVENT_DATE,
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic,
                    color = ArtisticMuted
                )

                Spacer(modifier = Modifier.height(22.dp))

                // Border Top Divider
                HorizontalDivider(
                    color = cardTheme.borderColor,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Footer Row: With Gratitude Ali Raza & Rotated Dark Emblem
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "WITH GRATITUDE,",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 2.sp,
                            color = ArtisticMuted
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = senderName,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = cardTheme.textColor
                        )
                    }

                    // Rotated dark badge with auto_awesome icon (Artistic Flair)
                    Surface(
                        modifier = Modifier
                            .size(48.dp)
                            .rotate(3f),
                        shape = RoundedCornerShape(16.dp),
                        color = ArtisticDarkAccent,
                        shadowElevation = 4.dp
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Artistic Sparkle",
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
