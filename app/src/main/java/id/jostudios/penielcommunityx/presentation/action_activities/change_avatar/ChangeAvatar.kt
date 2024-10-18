package id.jostudios.penielcommunityx.presentation.action_activities.change_avatar

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toFile
import dagger.hilt.android.AndroidEntryPoint
import id.jostudios.penielcommunityx.common.ContentManager
import id.jostudios.penielcommunityx.presentation.extras.Dialog.ErrorDialog
import id.jostudios.penielcommunityx.presentation.extras.Dialog.LoadingDialog
import id.jostudios.penielcommunityx.presentation.extras.components.ThemedButton
import id.jostudios.penielcommunityx.presentation.extras.components.home.ProfilePicture
import id.jostudios.penielcommunityx.presentation.ui.theme.PenielCommunityXTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangeAvatar : ComponentActivity() {

    private val viewModel: ChangeAvatarViewModel by viewModels();
    private var selectedUri: Uri? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PenielCommunityXTheme(
                window = window
            ) {
                Application();
            }
        }
    }

    @Composable
    fun Application() {
        val state = viewModel.state;
        val context = LocalContext.current;
        val imgPickerState = rememberLauncherForActivityResult(
            ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                imagePickerHandler(context, uri);
            });

        if (state.value.isLoading) {
            LoadingDialog();
        }

        if (state.value.isError != null) {
            ErrorDialog(error = state.value.isError!!, onDismiss = {
                viewModel.clearError();
            });
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Change Avatar", fontWeight = FontWeight.Bold, fontSize = 20.sp);

            Spacer(
                modifier = Modifier.height(30.dp)
            );

            LaunchedEffect(true) {
                viewModel.loadAvatar();
            }

            if (state.value.currentAvatarUri != null) {
                ProfilePicture(
                    state.value.currentAvatarUri!!,
                    modifier = Modifier
                        .height(128.dp)
                        .width(128.dp)
                );
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            );

            ThemedButton(
                text = "Hapus Avatar",
                onClick = {
                    try {

                        viewModel.removeAvatar();
                        Toast.makeText(context, "Avatar berhasil di hapus!", Toast.LENGTH_SHORT)
                            .show();

                    } catch (e: Exception) {
                        Toast.makeText(context, "Error : ${e.localizedMessage}", Toast.LENGTH_SHORT)
                            .show();
                    }
                }
            );

            ThemedButton(
                text = "Ganti Avatar",
                onClick = {
                    imgPickerState.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly));
                }
            );

            ThemedButton(
                text = "Save Avatar",
                onClick = {
                    saveAvatar(context);
                }
            );
        }
    }

    private fun saveAvatar(context: Context) {
        if (selectedUri == null) {
            return;
        }

        try {
            viewModel.uploadAvatar(context, selectedUri!!);
        } catch (e: Exception) {
            Toast.makeText(context, "Error : ${e.localizedMessage}", Toast.LENGTH_SHORT).show();
        }
    }

    private fun imagePickerHandler(context: Context, uri: Uri?) {
        if (uri == null) {
            return;
        }

        ContentManager.fileFromContentUri(context, uri);

        Log.d("Change Avatar Activity", "Selected Image : $uri");

        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri);

        Log.d("Change", "Bitmap Width : ${bitmap.width}");
        Log.d("Change", "Bitmap Height : ${bitmap.height}");

        viewModel.setCurrentUri(uri);
        selectedUri = uri;
    }
}