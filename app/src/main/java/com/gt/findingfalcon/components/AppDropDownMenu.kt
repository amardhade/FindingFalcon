package com.gt.findingfalcon.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import com.gt.findingfalcon.model.DropDownItem
import com.gt.findingfalcon.utilities.LocalSpacing
import java.util.Locale

@Composable
fun <T : Any> AppDropDownMenu(
    modifier: Modifier,
    dropDownList: MutableList<DropDownItem<T>>,
    placeholderText: String,
    labelText: String,
    minChar: Int = 2,
    loading: Boolean = false,
    onBack: () -> Unit = {},
    toggleDropdown: (selectedItem: DropDownItem<T>?, isExpanded: Boolean) -> Unit
) {
    val localSpacing = LocalSpacing.current
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var filterSuggestionItems: List<DropDownItem<T>>
    var isExpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<DropDownItem<T>?>(DropDownItem()) }
    var enteredText by remember { mutableStateOf("") }
    // To achieve click on OutlineTextField
    val interactionSource = remember { MutableInteractionSource() }
    var shouldFilterList by remember { mutableStateOf(false) }

    val trailingIcon = if (isExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    AppBackHandler() {
        if (isExpanded) isExpanded = false
        else onBack()
    }

    Column(modifier) {
        OutlinedTextField(
            value = enteredText,
            onValueChange = {
                enteredText = it
                shouldFilterList = true
            },
            enabled = !loading,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    textFieldSize = coordinates.size.toSize()
                },
            placeholder = { AppPlaceholderText(text = placeholderText, modifier = Modifier) },
            label = { AppPlaceholderText(text = labelText, modifier = Modifier) },
            trailingIcon = {
                if (loading)
                    CircularProgressIndicator(
                        modifier = Modifier.size(localSpacing.CircularSizeSmall),
                        strokeWidth = 2.dp,
                        color = Color.DarkGray
                    )
                else
                    Icon(trailingIcon, "contentDescription",
                        Modifier.clickable { isExpanded = !isExpanded })
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Black.copy(alpha = ContentAlpha.medium),
                unfocusedBorderColor = Black.copy(alpha = ContentAlpha.medium),
                cursorColor = Black
            ),
            interactionSource = interactionSource
        )
        // Click on OutlinedTextField
        if (interactionSource.collectIsPressedAsState().value) {
            isExpanded = true
            shouldFilterList = false
        }

        filterSuggestionItems = if (enteredText.length >= minChar && shouldFilterList) {
            dropDownList.filter { dropDownItem ->
                dropDownItem.title.lowercase(locale = Locale.getDefault()).contains(
                    enteredText.lowercase(locale = Locale.getDefault()),
                    ignoreCase = true
                )
            }
        } else
            dropDownList


        DropdownMenu(
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            },
            modifier = Modifier
                .width(with(localSpacing) { textFieldSize.width.dp })
                .shadow(0.dp)
        ) {
            if (filterSuggestionItems.isEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = localSpacing.spaceMedium)
                        .clickable { isExpanded = false },
                    backgroundColor = White,
                    elevation = 10.dp
                ) {
                    AppEmptyTextView(emptyText = "No items found")
                }
            }
            filterSuggestionItems.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        isExpanded = false
                        selectedItem = item
                        enteredText = selectedItem?.title!!
                        toggleDropdown(selectedItem, isExpanded)
                    },
                    modifier = Modifier
                        .background(color = White.copy(ContentAlpha.disabled))
                        .padding(0.dp)
                ) {
                    Row {
                        AppTextField(
                            text = item.title,
                            fontWeight = if (item.title.equals(
                                    enteredText,
                                    false
                                )
                            ) FontWeight.Bold else FontWeight.Normal
                        )
//                        AppDivider()
                    }
                }
            }
        }
    }
}