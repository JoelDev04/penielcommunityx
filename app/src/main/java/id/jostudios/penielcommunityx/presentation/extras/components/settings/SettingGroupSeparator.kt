package id.jostudios.penielcommunityx.presentation.extras.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.jostudios.penielcommunityx.presentation.ui.theme.softDarkBlue

@Composable
fun SettingGroupSeparator(
    name: String
) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(text = name, textAlign = TextAlign.Left, fontSize = 18.sp);
        Box(modifier = Modifier.height(2.dp).background(Brush.horizontalGradient(colors = listOf(
            softDarkBlue,
            softDarkBlue
        )), alpha = 0.6f).fillMaxWidth());
    }
}