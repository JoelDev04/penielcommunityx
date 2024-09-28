package id.jostudios.penielcommunityx.presentation.screens.home_screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.jostudios.penielcommunityx.R
import id.jostudios.penielcommunityx.common.States
import id.jostudios.penielcommunityx.presentation.CheatActivity
import id.jostudios.penielcommunityx.presentation.MainActivity
import id.jostudios.penielcommunityx.presentation.MembersActivity
import id.jostudios.penielcommunityx.presentation.extras.Dialog.ErrorDialog
import id.jostudios.penielcommunityx.presentation.extras.Dialog.LoadingDialog
import id.jostudios.penielcommunityx.presentation.extras.Dialog.ProfilePictureDialog
import id.jostudios.penielcommunityx.presentation.extras.components.home.ActionButtonContainer
import id.jostudios.penielcommunityx.presentation.extras.components.home.ActionButtonModel
import id.jostudios.penielcommunityx.presentation.extras.components.home.Banner
import id.jostudios.penielcommunityx.presentation.extras.components.home.TopAction
import id.jostudios.penielcommunityx.presentation.extras.components.home.ProfilePicture
import id.jostudios.penielcommunityx.presentation.ui.theme.matchaSage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    activity: MainActivity,
    viewModel: HomeViewModel
) {
    val context = LocalContext.current;
    val state = viewModel.state;
    var profilePictureState = remember {
        mutableStateOf(false)
    };

    if (state.value.isLoading) {
        LoadingDialog();
    }
    if (state.value.error != null) {
        ErrorDialog(error = state.value.error!!, onDismiss = {
            viewModel.closeError();

            if (!state.value.isValidVersion) {
                Toast.makeText(context, "Please update your app first!", Toast.LENGTH_LONG).show();
                activity.finish();
            }
        });
    }

    Column {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.4f)
                .background(matchaSage),

            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Top Display
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Profile Info
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier
                        .background(Color.Blue, shape = RoundedCornerShape(50))
                        .requiredHeight(32.dp)
                        .requiredWidth(32.dp)
                    ) {

                        // Profile Picture
                        if (state.value.profileUri != null) {
                            ProfilePicture(state.value.profileUri!!, modifier = Modifier.clickable {
                                profilePictureState.value = true;
                            });

                            if (profilePictureState.value) {
                                ProfilePictureDialog(profileUri = state.value.profileUri!!, onDismiss = { profilePictureState.value = false; });
                            }
                        } else {
                            Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Default Profile Picture",
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(50))
                            );
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp));

                    Text(text = state.value.displayName, color = Color.White, fontSize = 14.sp);
                }

                // Top Actions
                TopAction(activity, viewModel)
            }
            
            Spacer(modifier = Modifier.fillMaxHeight(0.25f))

            // Banner
            if (state.value.bannerUri != null) {
                Banner(bannerUri = state.value.bannerUri!!)
            } else {
                Column(modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.5f)
                    .requiredHeight(140.dp)
                    .background(Color.White, shape = RoundedCornerShape(20.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){}
            }
        }

        // Collections

        if (!States.isCheatApp) {
            ActionButtonContainer(
                modifier = Modifier.weight(1f),
                count = 8,
                buttons = listOf(
                    ActionButtonModel(LocalContext.current, "Members", MembersActivity::class.java),
                    ActionButtonModel(LocalContext.current, "Pembangunan", null),
                    ActionButtonModel(LocalContext.current, "Diakonia", null),
                    ActionButtonModel(LocalContext.current, "Warta Jemaat", null),
                    ActionButtonModel(LocalContext.current, "Pelayan", null),
                    ActionButtonModel(LocalContext.current, "Renungan", null),
                    ActionButtonModel(LocalContext.current, "Groups", null),
                    ActionButtonModel(LocalContext.current, "Jadwal", null),
                )
            );
        } else {
            ActionButtonContainer(
                modifier = Modifier.weight(1f),
                count = 9,
                buttons = listOf(
                    ActionButtonModel(LocalContext.current, "Members", MembersActivity::class.java),
                    ActionButtonModel(LocalContext.current, "Pembangunan", null),
                    ActionButtonModel(LocalContext.current, "Diakonia", null),
                    ActionButtonModel(LocalContext.current, "Warta Jemaat", null),
                    ActionButtonModel(LocalContext.current, "Pelayan", null),
                    ActionButtonModel(LocalContext.current, "Renungan", null),
                    ActionButtonModel(LocalContext.current, "Groups", null),
                    ActionButtonModel(LocalContext.current, "Jadwal", null),
                    ActionButtonModel(LocalContext.current, "Cheat", CheatActivity::class.java)
                )
            );
        }
    }
}