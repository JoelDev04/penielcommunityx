package id.jostudios.penielcommunityx.presentation.extras.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import id.jostudios.penielcommunityx.presentation.ui.theme.softBlack
import id.jostudios.penielcommunityx.presentation.ui.theme.softDarkBlue
import id.jostudios.penielcommunityx.presentation.ui.theme.softLightBlue

@Composable
fun ThemedButton(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(15.dp),
    onClick: () -> Unit
) {
    Button(
        shape = shape,
        modifier = modifier,
        onClick = onClick,
        colors = ButtonColors(
            containerColor = softDarkBlue,
            disabledContainerColor = softDarkBlue,
            contentColor = Color.White,
            disabledContentColor = softBlack
        )
    ) {
        Text(text = text);
    }
}