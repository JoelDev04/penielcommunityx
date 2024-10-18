package id.jostudios.penielcommunityx.presentation.extras.components.home

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import id.jostudios.penielcommunityx.domain.enums.PermissionsEnum

data class ActionButtonModel(
    val context: Context,
    val name: String,
    val intentClass: Class<*>?
)

@Composable
fun ActionButton(
    context: Context,
    name: String,
    intentClass: Class<*>?,
    requiredPermission: PermissionsEnum? = null
) {
    val width = 120.dp;
    val height = 120.dp;

    Button(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(width)
            .height(height),
        onClick = {
            if (intentClass == null) {
                Toast.makeText(context, "Fitur belum tersedia!", Toast.LENGTH_SHORT).show();
                return@Button
            }

            val intent = Intent(context, intentClass);
            context.startActivity(intent);
        }) {
            Text(text = name, textAlign = TextAlign.Center);
    }
}