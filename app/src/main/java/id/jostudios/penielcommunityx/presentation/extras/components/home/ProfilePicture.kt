package id.jostudios.penielcommunityx.presentation.extras.components.home

import android.net.Uri
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.jostudios.penielcommunityx.R

@Composable
fun ProfilePicture(
    profileUri: Uri,
    modifier: Modifier = Modifier
) {
    // Profile Picture

    AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(profileUri)
            .crossfade(true)
            .placeholder(R.drawable.ic_launcher_background)
            .build(),
        contentDescription = "Profile picture",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(shape = RoundedCornerShape(50)))

//  Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "ProfilePicture");
}