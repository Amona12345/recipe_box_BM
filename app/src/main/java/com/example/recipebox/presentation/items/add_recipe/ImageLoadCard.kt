package com.example.recipebox.presentation.items.add_recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import compose.icons.FeatherIcons
import compose.icons.feathericons.Camera

@Composable
fun ImageUploadCard(
    imageUri: String?,
    onImageClick: () -> Unit,
    onImageRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clickable { onImageClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (imageUri != null) Color.Transparent else Color(0xFFF2F2F2) // fallback color
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Recipe Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp)),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.3f)
                                )
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                )

                IconButton(
                    onClick = onImageRemove,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .background(
                            Color.Black.copy(alpha = 0.7f),
                            CircleShape
                        )
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remove image",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    color = Color.White.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = Color(0xFF6200EE),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Edit Photo",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF6200EE)
                            )
                        )
                    }
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(
                                brush = Brush.linearGradient(listOf(Color(0xFF7C4DFF), Color(0xFF448AFF))),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = FeatherIcons.Camera,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp),
                            tint = Color.White
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Add Recipe Photo",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = "Tap to capture or select from gallery",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Gray
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

