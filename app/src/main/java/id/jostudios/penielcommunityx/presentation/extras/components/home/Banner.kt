package id.jostudios.penielcommunityx.presentation.extras.components.home

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.jostudios.penielcommunityx.R

@Composable
fun Banner(
    bannerUri: Uri
) {
    Column(modifier = Modifier
        .fillMaxWidth(0.8f)
        .fillMaxHeight(0.5f)
        .requiredHeight(140.dp)
        .background(Color.White, shape = RoundedCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(bannerUri)
                .crossfade(true)
                .placeholder(R.drawable.ic_launcher_background)
                .build(),
            contentDescription = "Banner",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
        );

//        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "ProfilePicture",
//            modifier = Modifier
//                .clip(shape = RoundedCornerShape(20.dp)))
    }
}