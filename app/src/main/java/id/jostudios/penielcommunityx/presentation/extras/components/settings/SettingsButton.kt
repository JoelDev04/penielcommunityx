package id.jostudios.penielcommunityx.presentation.extras.components.settings

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import id.jostudios.penielcommunityx.presentation.ui.theme.softBlack

@Composable
fun SettingsButton(
    text: String,
    target: Class<*>? = null,
    extras: Intent? = null
) {
    val context = LocalContext.current;

    Button(
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonColors(
            disabledContainerColor = Color.Transparent,
            contentColor = softBlack,
            disabledContentColor = softBlack,
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(0),
        onClick = {
            if (target == null) { return@Button }

            val targetIntent = Intent(context, target);

            if (extras != null) {
                targetIntent.putExtras(extras);
            }

            context.startActivity(targetIntent);
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = text, textAlign = TextAlign.Left);
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Right Arrow");
        }
    }
}