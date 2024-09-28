package id.jostudios.penielcommunityx.presentation.extras.Dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun ErrorDialog(
    error: String,
    onDismiss: () -> Unit
) {
    Dialog(
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false, usePlatformDefaultWidth = true),
        onDismissRequest = { onDismiss(); }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(Modifier.padding(20.dp)) {
                Row {
                    Text(text = "Error!", fontWeight = FontWeight.Bold, fontSize = 20.sp);
                }

                Spacer(modifier = Modifier.height(10.dp));

                Text(text = error);

                Spacer(modifier = Modifier.height(10.dp));

                TextButton(onClick = {
                    onDismiss();
                }) {
                    Text(text = "Close");
                }
            }
        }
    }
}