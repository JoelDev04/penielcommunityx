package id.jostudios.penielcommunityx.presentation.action_activities.diakonia_form

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import id.jostudios.penielcommunityx.common.PermissionManager
import id.jostudios.penielcommunityx.common.TextFormat
import id.jostudios.penielcommunityx.domain.enums.PermissionsEnum
import id.jostudios.penielcommunityx.presentation.extras.Dialog.LoadingDialog
import id.jostudios.penielcommunityx.presentation.extras.components.ThemedButton
import id.jostudios.penielcommunityx.presentation.extras.components.member_form.FormTextInput
import id.jostudios.penielcommunityx.presentation.ui.theme.PenielCommunityXTheme

@AndroidEntryPoint
class DiakoniaForm : ComponentActivity() {
    private val viewModel: DiakoniaFormViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PenielCommunityXTheme(
                window = window
            ) {
                Application()
            }
        }
    }

    @Composable
    fun Application() {
        val context = LocalContext.current;
        val state = viewModel.state;

        if (state.value.isLoading) {
            LoadingDialog();
        }

        LaunchedEffect(true) {
            viewModel.fetchUserList();
        }

        Column(modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Diakonia Form", fontWeight = FontWeight.SemiBold, fontSize = 28.sp);
            }
            Spacer(modifier = Modifier.height(20.dp));

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                FormTextInput(
                    label = "Username",
                    value = if (state.value.username == null) "" else state.value.username.toString(),
                    onChange = {
                        viewModel.setId(it);
                    }
                )

                Spacer(modifier = Modifier.height(10.dp));

                FormTextInput(
                    label = "Amount Paid",
                    value = if (state.value.amountPaid == null) "" else state.value.amountPaid!!,
                    onChange = {
                        viewModel.setAmountPaid(it);
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ThemedButton(
                text = "Save",
                onClick = {
                    Toast.makeText(context, "Saving payment!", Toast.LENGTH_LONG).show();
                    viewModel.saveDiakonia();
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}