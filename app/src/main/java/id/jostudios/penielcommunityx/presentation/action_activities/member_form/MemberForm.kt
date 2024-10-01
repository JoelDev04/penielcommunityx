package id.jostudios.penielcommunityx.presentation.action_activities.member_form

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import id.jostudios.penielcommunityx.presentation.extras.Dialog.ErrorDialog
import id.jostudios.penielcommunityx.presentation.extras.Dialog.LoadingDialog
import id.jostudios.penielcommunityx.presentation.extras.components.ThemedButton
import id.jostudios.penielcommunityx.presentation.ui.theme.PenielCommunityXTheme

@AndroidEntryPoint
class MemberForm : ComponentActivity() {

    private val viewModel: MemberFormViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uid = intent.getStringExtra("uid");

        viewModel.loadUser(uid!!);

        setContent {
            PenielCommunityXTheme(
                window = window
            ) {
                Application();
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Application() {
        val state by remember {
            viewModel.state
        };
        val context = LocalContext.current;

        if (state.error != null) {
            ErrorDialog(error = state.error!!) {
                viewModel.clearError();
            }
        }
        if (viewModel.state.value.currentUser == null) {
            LoadingDialog();
            return;
        }

        val currentUser = viewModel.state.value.currentUser!!;

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Member Form", fontSize = 28.sp, fontWeight = FontWeight.SemiBold);
            Spacer(modifier = Modifier.height(35.dp));

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // Name
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Name", fontSize = 16.sp);
                    Spacer(modifier = Modifier.width(40.dp));
                    Text(text = ":", fontSize = 16.sp);
                    Spacer(modifier = Modifier.width(10.dp));
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = "@${currentUser.name}",
                        onValueChange = {},
                        readOnly = true,
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 12.sp
                        )
                    );
                }

                Spacer(modifier = Modifier.height(10.dp));

                // Display Name
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Display Name", fontSize = 16.sp);
                    Spacer(modifier = Modifier.width(40.dp));
                    Text(text = ":", fontSize = 16.sp);
                    Spacer(modifier = Modifier.width(10.dp));
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.displayName!!,
                        onValueChange = {
                            viewModel.setDisplayName(it);
                            state.displayName
                        },
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 12.sp
                        )
                    );
                }

                Spacer(modifier = Modifier.height(10.dp));

                // Email
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Email", fontSize = 16.sp);
                    Spacer(modifier = Modifier.width(40.dp));
                    Text(text = ":", fontSize = 16.sp);
                    Spacer(modifier = Modifier.width(10.dp));
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.email!!,
                        onValueChange = {
                            viewModel.setEmail(it);
                        },
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 12.sp
                        )
                    );
                }

                Spacer(modifier = Modifier.height(10.dp));

                // Phone Number
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Phone Number", fontSize = 16.sp);
                    Spacer(modifier = Modifier.width(40.dp));
                    Text(text = ":", fontSize = 16.sp);
                    Spacer(modifier = Modifier.width(10.dp));
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.phoneNumber!!,
                        onValueChange = {
                            viewModel.setPhoneNumber(it);
                        },
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 12.sp
                        )
                    );
                }

                Spacer(modifier = Modifier.height(10.dp));

                // Role
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Role", fontSize = 16.sp);
                    Spacer(modifier = Modifier.width(40.dp));
                    Text(text = ":", fontSize = 16.sp);
                    Spacer(modifier = Modifier.width(10.dp));
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = currentUser.role.name,
                        onValueChange = {},
                        readOnly = true,
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 12.sp
                        )
                    );
                }

                Spacer(modifier = Modifier.height(10.dp));

                // Birth Date
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val datePickerState = rememberDatePickerState();
                    datePickerState.selectedDateMillis = currentUser.birthDate;
                    datePickerState.displayMode = DisplayMode.Input;

                    Text(text = "Birth Date", fontSize = 16.sp);
                    Spacer(modifier = Modifier.width(40.dp));
                    Text(text = ":", fontSize = 16.sp);
                    Spacer(modifier = Modifier.width(10.dp));
                    DatePicker(state = datePickerState);
                }

                Spacer(modifier = Modifier.height(10.dp));
            }

            ThemedButton(
                text = "Save",
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Toast.makeText(context, "Saving changes", Toast.LENGTH_SHORT).show();
                viewModel.updateDetails();
            }
        }
    }
}