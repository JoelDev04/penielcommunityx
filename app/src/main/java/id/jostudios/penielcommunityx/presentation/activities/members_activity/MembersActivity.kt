package id.jostudios.penielcommunityx.presentation.activities.members_activity

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.jostudios.penielcommunityx.common.PermissionManager
import id.jostudios.penielcommunityx.domain.enums.PermissionsEnum
import id.jostudios.penielcommunityx.presentation.MemberDetailsActivity
import id.jostudios.penielcommunityx.presentation.extras.Dialog.ErrorDialog
import id.jostudios.penielcommunityx.presentation.extras.Dialog.LoadingDialog
import id.jostudios.penielcommunityx.presentation.extras.components.home.ProfilePicture

@Composable
fun MembersActivity(
    viewModel: MembersViewModel
) {
    val state = viewModel.state;
    val context = LocalContext.current;

    if (state.value.isLoading) {
        LoadingDialog();
    }
    if (state.value.error != null) {
        ErrorDialog(error = state.value.error!!, onDismiss = {
            viewModel.closeError();
        });
    }

    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Anggota Gereja",
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier
                    .height(32.dp)
            );

            if (PermissionManager.checkPermission(PermissionsEnum.AddMembers)) {
                Icon(Icons.Default.Add, contentDescription = "Add Member", modifier = Modifier.clickable {
                    if (PermissionManager.checkPermission(PermissionsEnum.AddMembers)) {
                        Toast.makeText(context, "Proceeding!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "You're not allowed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        Spacer(modifier = Modifier
            .height(35.dp));

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.value.isLoading && state.value.members == null) {
                Text(text = "Loading members! Please wait...");
            } else if (!state.value.isLoading && state.value.members == null) {
                Text(text = "Cannot load member list!");
                Text(text = state.value.error!!);
            } else if (!state.value.isLoading && state.value.members != null) {
                LazyColumn {
                    items(
                        count = state.value.members?.size!!
                    ) { i ->
                        val currentMember = state.value.members!![i];
                        val context = LocalContext.current;

                        Card(
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                                .clickable {
                                    val detailsActivity =
                                        Intent(context, MemberDetailsActivity::class.java);
                                    detailsActivity.putExtra("user_id", currentMember.id);

                                    context.startActivity(detailsActivity);
                                },
                            shape = RoundedCornerShape(20f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val profilePictureState = remember {
                                    mutableStateOf<Uri?>(null);
                                }

                                LaunchedEffect(key1 = true) {
                                    profilePictureState.value = viewModel.fetchUserProfile(currentMember.profilePicture);
                                }

                                profilePictureState.value?.let { profileUri ->
                                    ProfilePicture(profileUri = profileUri, modifier = Modifier
                                        .width(28.dp)
                                        .height(28.dp));
                                }

                                Text(text = "@${currentMember.name}", fontSize = 10.sp);
                                Text(text = currentMember.displayName, fontSize = 10.sp);
                                Text(text = currentMember.groups[0].name, fontSize = 10.sp);
                            }
                        }
                    }
                }
            }
        }
    }
}