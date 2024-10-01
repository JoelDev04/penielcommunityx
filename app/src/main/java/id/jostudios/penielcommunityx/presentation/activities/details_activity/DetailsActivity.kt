package id.jostudios.penielcommunityx.presentation.activities.details_activity

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.jostudios.penielcommunityx.common.PermissionManager
import id.jostudios.penielcommunityx.common.States
import id.jostudios.penielcommunityx.domain.enums.PermissionsEnum
import id.jostudios.penielcommunityx.presentation.action_activities.member_form.MemberForm
import id.jostudios.penielcommunityx.presentation.extras.Dialog.ErrorDialog
import id.jostudios.penielcommunityx.presentation.extras.Dialog.LoadingDialog
import id.jostudios.penielcommunityx.presentation.extras.Dialog.ProfilePictureDialog
import id.jostudios.penielcommunityx.presentation.extras.components.home.ProfilePicture
import id.jostudios.penielcommunityx.presentation.ui.theme.matchaSage
import id.jostudios.penielcommunityx.presentation.ui.theme.softDarkBlue
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun DetailsActivity(
    viewModel: DetailsViewModel
) {
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
        })
        return;
    }

    Column {
        Row(modifier = Modifier
            .background(matchaSage)
            .fillMaxSize()
            .weight(0.5f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(shape = RoundedCornerShape(50), modifier = Modifier
                .width(182.dp)
                .height(182.dp)
                .padding(10.dp),
                backgroundColor = softDarkBlue
            ) {
                Box(modifier = Modifier.padding(8.dp)) {
                    state.value.profileUri.let { profileUri ->
                        if (profileUri == null ) {
                            return@let
                        }

                        ProfilePicture(profileUri = profileUri, modifier = Modifier
                            .width(182.dp)
                            .height(182.dp)
                            .clickable {
                                profilePictureState.value = true;
                            }
                        );

                        if (profilePictureState.value) {
                            ProfilePictureDialog(profileUri = state.value.profileUri!!, onDismiss = { profilePictureState.value = false; });
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.padding(15.dp)
            ) {
                val context = LocalContext.current;
                if (PermissionManager.checkPermission(PermissionsEnum.EditMembers)) {
                    Icon(
                        Icons.Default.Create,
                        contentDescription = "Edit Member",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            Toast.makeText(context, "Edit Member", Toast.LENGTH_SHORT).show();
                            val targetIntent = Intent(context, MemberForm::class.java);
                            targetIntent.putExtra("uid", state.value.userModel?.id);
                            context.startActivity(targetIntent);
                        }
                    );
                }
            }
        }
        Column(modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(20.dp)
            .weight(1f)
        ) {
            val fontSize = 14.sp;

            Text(text = "Detail anggota", fontSize = 28.sp, fontWeight = FontWeight.Bold);
            Spacer(modifier = Modifier.height(20.dp));

            if (state.value.userModel?.id == null) {
                return;
            }

            Row {
                Text(text = "ID", fontSize = fontSize, modifier = Modifier.fillMaxWidth(0.3f));
                Text(text = ":", fontSize = fontSize);
                Spacer(modifier = Modifier.width(20.dp));
                Text(text = state.value.userModel?.id!!, fontSize = fontSize);
            }

            Row {
                Text(text = "Name", fontSize = fontSize, modifier = Modifier.fillMaxWidth(0.3f));
                Text(text = ":", fontSize = fontSize);
                Spacer(modifier = Modifier.width(20.dp));
                Text(text = "@${state.value.userModel?.name!!}", fontSize = fontSize);
            }

            Row {
                Text(text = "Display Name", fontSize = fontSize, modifier = Modifier.fillMaxWidth(0.3f));
                Text(text = ":", fontSize = fontSize);
                Spacer(modifier = Modifier.width(20.dp));
                Text(text = state.value.userModel?.displayName!!, fontSize = fontSize);
            }

            Row {
                Text(text = "Role", fontSize = fontSize, modifier = Modifier.fillMaxWidth(0.3f));
                Text(text = ":", fontSize = fontSize);
                Spacer(modifier = Modifier.width(20.dp));
                Text(text = state.value.userModel?.role?.name ?: "-", fontSize = fontSize);
            }

            Row {
                Text(text = "Tanggal Lahir", fontSize = fontSize, modifier = Modifier.fillMaxWidth(0.3f));
                Text(text = ":", fontSize = fontSize);
                Spacer(modifier = Modifier.width(20.dp));

                val date = Date(state.value.userModel?.birthDate!!);

                if (date.time == 0.toLong()) {
                    Text(text = "-", fontSize = fontSize);
                } else {
                    val formatter = SimpleDateFormat("EE dd, MM MMM yyyy");
                    val formatted = formatter.format(date);

                    Text(text = formatted, fontSize = fontSize);
                }
            }

            Row {
                Text(text = "Email", fontSize = fontSize, modifier = Modifier.fillMaxWidth(0.3f));
                Text(text = ":", fontSize = fontSize);
                Spacer(modifier = Modifier.width(20.dp));
                Text(text = state.value.userModel?.email ?: "-", fontSize = fontSize);
            }

            Row {
                Text(text = "Nomor HP", fontSize = fontSize, modifier = Modifier.fillMaxWidth(0.3f));
                Text(text = ":", fontSize = fontSize);
                Spacer(modifier = Modifier.width(20.dp));
                Text(text = state.value.userModel?.phoneNumber ?: "-", fontSize = fontSize);
            }

            Row {
                Text(text = "Groups", fontSize = fontSize, modifier = Modifier.fillMaxWidth(0.3f));
                Text(text = ":", fontSize = fontSize);
                Spacer(modifier = Modifier.width(20.dp));

                Column {
                    state.value.userModel?.groups?.onEach {
                        Text(text = it.name, fontSize = fontSize);
                    }
                }
            }
        }
    }
}