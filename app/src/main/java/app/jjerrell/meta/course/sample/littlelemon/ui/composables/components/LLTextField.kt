package app.jjerrell.meta.course.sample.littlelemon.ui.composables.components

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.textFieldContainer
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.LittleLemonTheme
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.Primary1
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.Secondary1

@Immutable
object LLTextFieldDefaults {
    val shape: Shape = RoundedCornerShape(8.dp)

    @Composable
    fun borderColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        return rememberUpdatedState(
            when {
                !enabled -> Primary1
                isError -> Secondary1
                focused -> Primary1
                else -> Primary1
            }
        )
    }

    @Composable
    fun colors(
        containerColor: Color = MaterialTheme.colorScheme.textFieldContainer,
        indicatorColor: Color = Color.Transparent,
        errorTextColor: Color = Color.Red
    ): TextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = containerColor,
        unfocusedContainerColor = containerColor,
        disabledContainerColor = containerColor,
        errorContainerColor = containerColor,
        disabledIndicatorColor = indicatorColor,
        errorIndicatorColor = indicatorColor,
        focusedIndicatorColor = indicatorColor,
        unfocusedIndicatorColor = indicatorColor,
        errorTextColor = errorTextColor
    )
}

@Composable
fun LLTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = LLTextFieldDefaults.shape,
    colors: TextFieldColors = LLTextFieldDefaults.colors()
) {
   TextField(
       value = value,
       onValueChange = onValueChange,
       modifier = modifier
           .border(
               1.dp,
               LLTextFieldDefaults.borderColor(
                   enabled = enabled,
                   isError = isError,
                   interactionSource = interactionSource
               ).value,
               shape = LLTextFieldDefaults.shape
           ),
       enabled = enabled,
       readOnly = readOnly,
       textStyle = textStyle,
       label = label,
       placeholder = placeholder,
       leadingIcon = leadingIcon,
       trailingIcon = trailingIcon,
       prefix = prefix,
       suffix = suffix,
       supportingText = supportingText,
       isError = isError,
       visualTransformation = visualTransformation,
       keyboardOptions = keyboardOptions,
       keyboardActions = keyboardActions,
       singleLine = singleLine,
       maxLines = maxLines,
       minLines = minLines,
       interactionSource = interactionSource,
       shape = shape,
       colors = colors,
   )
}

@Preview(showBackground = true)
@Composable
private fun LLTextField_Preview() {
    var input by remember { mutableStateOf("Current input") }
    LittleLemonTheme {
        Surface(
            modifier = Modifier.padding(24.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(60.dp)
            ) {
                LLTextField(
                    value = input,
                    onValueChange = { input = it }
                )
                LLTextField(
                    value = "",
                    onValueChange = { },
                    placeholder = {
                        Text("Placeholder Text")
                    }
                )
                LLTextField(
                    value = "Error",
                    onValueChange = { },
                    isError = true
                )
                LLTextField(
                    value = "Read Only",
                    onValueChange = { },
                    label = {
                        Text("Placeholder Text")
                    },
                    readOnly = true
                )
                LLTextField(
                    value = "Disabled",
                    onValueChange = { },
                    enabled = false
                )
            }
        }
    }
}