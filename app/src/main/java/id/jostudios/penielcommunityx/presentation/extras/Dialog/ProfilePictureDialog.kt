package id.jostudios.penielcommunityx.presentation.extras.Dialog

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import id.jostudios.penielcommunityx.presentation.extras.components.home.ProfilePicture

@Composable
fun ProfilePictureDialog(
    profileUri: Uri,
    onDismiss: () -> Unit
) {
    Dialog(
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true, usePlatformDefaultWidth = true),
        onDismissRequest = { onDismiss(); }
    ) {
        Column(modifier = Modifier
            .padding(20.dp)
            .width(300.dp)
            .height(300.dp)
        ) {
            ProfilePicture(profileUri = profileUri, modifier = Modifier.fillMaxWidth());
        }
    }
}