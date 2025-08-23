package com.example.recipebox.presentation.items.add_recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.recipebox.ui.theme.RecipeColors
import com.example.recipebox.ui.theme.RecipeTypography

@Composable
fun TextFieldCust(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: ImageVector,
    isRequired: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = 1,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    supportingText: String? = null,
    isError: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = if (isFocused) RecipeColors.Primary else RecipeColors.OnSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )

            Text(
                text = label,
                style = RecipeTypography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = if (isFocused) RecipeColors.Primary else RecipeColors.OnSurface
                )
            )

            if (isRequired) {
                Text(
                    text = "*",
                    style = RecipeTypography.bodyMedium,
                    color = RecipeColors.Error
                )
            }
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = RecipeTypography.bodyMedium,
                    color = RecipeColors.OnSurfaceVariant.copy(alpha = 0.7f)
                )
            },
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            color = if (isFocused) RecipeColors.Primary.copy(alpha = 0.1f)
                            else RecipeColors.SurfaceVariant,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = if (isFocused) RecipeColors.Primary else RecipeColors.OnSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused },
            minLines = minLines,
            maxLines = maxLines,
            singleLine = maxLines == 1,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            isError = isError,
            supportingText = supportingText?.let { { Text(it) } },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = RecipeColors.Primary,
                focusedLabelColor = RecipeColors.Primary,
                unfocusedBorderColor = RecipeColors.Outline,
                errorBorderColor = RecipeColors.Error,
                focusedLeadingIconColor = RecipeColors.Primary,
                unfocusedLeadingIconColor = RecipeColors.OnSurfaceVariant
            ),
            shape = RoundedCornerShape(16.dp)
        )
    }
}