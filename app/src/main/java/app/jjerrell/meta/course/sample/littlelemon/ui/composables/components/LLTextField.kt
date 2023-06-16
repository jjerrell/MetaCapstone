package app.jjerrell.meta.course.sample.littlelemon.ui.composables.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.textFieldContainer
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.LittleLemonTheme

@Immutable
object LLTextFieldDefaults {
    val shape: Shape = RoundedCornerShape(8.dp)

    @Composable
    fun colors(
        focusedBorderColor: Color = MaterialTheme.colorScheme.onSecondary,
        unfocusedBorderColor: Color = MaterialTheme.colorScheme.primary,
        disabledBorderColor: Color = MaterialTheme.colorScheme.onPrimary,
        errorBorderColor: Color = MaterialTheme.colorScheme.secondary,
        containerColor: Color = MaterialTheme.colorScheme.textFieldContainer
    ): TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = focusedBorderColor,
        unfocusedBorderColor = unfocusedBorderColor,
        disabledBorderColor = disabledBorderColor,
        errorBorderColor = errorBorderColor,
        focusedContainerColor = containerColor,
        unfocusedContainerColor = containerColor,
        disabledContainerColor = containerColor,
        errorContainerColor = containerColor
    )
}

@Composable
fun LLTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
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
   OutlinedTextField(
       value = value,
       onValueChange = onValueChange,
       modifier = modifier,
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
                    value = TextFieldValue(input),
                    onValueChange = { input = it.text }
                )
                LLTextField(
                    value = TextFieldValue(),
                    onValueChange = { },
                    placeholder = {
                        Text("Placeholder Text")
                    }
                )
                LLTextField(
                    value = TextFieldValue("Error"),
                    onValueChange = { },
                    isError = true
                )
                LLTextField(
                    value = TextFieldValue("Read Only"),
                    onValueChange = { },
                    readOnly = true
                )
                LLTextField(
                    value = TextFieldValue("Disabled"),
                    onValueChange = { },
                    enabled = false
                )
            }
        }
    }
}