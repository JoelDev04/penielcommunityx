package id.jostudios.penielcommunityx.presentation.action_activities.member_form

import android.os.Bundle
import android.util.Log
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
import id.jostudios.penielcommunityx.presentation.extras.components.member_form.FormTextInput
import id.jostudios.penielcommunityx.presentation.ui.theme.PenielCommunityXTheme

@AndroidEntryPoint
class MemberForm : ComponentActivity() {

    private val viewModel: MemberFormViewModel by viewModels();
    private var uid: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        uid = intent.getStringExtra("uid");
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
        if (state.isLoading) {
            LoadingDialog();
        }
        if (state.isClose) {
            finish();
        }
        if (viewModel.state.value.currentUser == null || viewModel.state.value.displayName == null) {
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
                if (uid == null || uid == "") {
                    Log.d("MemberForm", "Add member mode!");
                    FormTextInput(
                        label = "Name",
                        value = state.name!!,
                        isStatic = false,
                        onChange = {
                            viewModel.setName(it)
                        }
                    );
                } else {
                    FormTextInput(
                        label = "Name",
                        value = "@${currentUser.name}",
                        isStatic = true,
                        onChange = {}
                    );
                }

                Spacer(modifier = Modifier.height(10.dp));

                // Password
                if (uid == null || uid == "") {
                    Log.d("MemberForm", "Add member mode!");
                    FormTextInput(
                        label = "Password",
                        value = state.password!!,
                        isStatic = false,
                        onChange = {
                            viewModel.setPassword(it)
                        }
                    );
                }

                Spacer(modifier = Modifier.height(10.dp));

                // Display Name
                FormTextInput(
                    label = "Display Name",
                    value = state.displayName!!,
                    onChange = {
                        viewModel.setDisplayName(it);
                    }
                );

                Spacer(modifier = Modifier.height(10.dp));

                // Email
                FormTextInput(
                    label = "Email",
                    value = state.email!!,
                    onChange = {
                        viewModel.setEmail(it);
                    }
                );

                Spacer(modifier = Modifier.height(10.dp));

                // Phone Number
                FormTextInput(
                    label = "Phone Number",
                    value = state.phoneNumber!!,
                    onChange = {
                        viewModel.setPhoneNumber(it);
                    }
                );

                Spacer(modifier = Modifier.height(10.dp));

                // Role
                if (uid == null || uid == "") {
                    Log.d("MemberForm", "Add member mode!");
                    FormTextInput(
                        label = "Role",
                        value = state.role!!,
                        isStatic = false,
                        onChange = {
                            viewModel.setRole(it);
                        }
                    );
                } else {
                    FormTextInput(
                        label = "Role",
                        value = currentUser.role.name,
                        isStatic = true,
                        onChange = {}
                    );
                }

                Spacer(modifier = Modifier.height(10.dp));

                // Birth Date
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val datePickerState = rememberDatePickerState(state.birthDate);

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        datePickerState.displayMode = DisplayMode.Input;

                        Text(text = "Birth Date", fontSize = 16.sp);
                        Spacer(modifier = Modifier.width(10.dp));
                        Text(text = ":", fontSize = 16.sp);
                        Spacer(modifier = Modifier.width(10.dp));
                        DatePicker(state = datePickerState);
                        Spacer(modifier = Modifier.width(10.dp));
                    }

                    ThemedButton(
                        text = "Save Date",
                        onClick = {
                            Toast.makeText(context, "Date saved!", Toast.LENGTH_SHORT).show();
                            viewModel.setBirthDate(datePickerState.selectedDateMillis!!);
                        }
                    );

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