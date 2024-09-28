package id.jostudios.penielcommunityx.presentation.activities.cheat_activity

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.jostudios.penielcommunityx.presentation.extras.components.ThemedButton
import id.jostudios.penielcommunityx.presentation.ui.theme.rubikFontSet

@Composable
fun CheatActivity(
    viewModel: CheatViewModel
) {
    val context = LocalContext.current;

    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Text(
            text = "Cheats",
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        );

        Spacer(modifier = Modifier.height(10.dp));

        ThemedButton(
            text = "Give All Permissions",
            onClick = {
                viewModel.giveAllPermissions()
                Toast.makeText(context, "Giving all permissions to current user!", Toast.LENGTH_LONG).show();
            }
        )

        Spacer(modifier = Modifier.height(5.dp));

        ThemedButton(
            text = "Default Permission",
            onClick = {
                viewModel.defaultPermissions()
                Toast.makeText(context, "Giving default permissions to current user!", Toast.LENGTH_LONG).show();
            }
        )
    }
}