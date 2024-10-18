package id.jostudios.penielcommunityx.presentation.activities.diakonia

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import id.jostudios.penielcommunityx.common.PermissionManager
import id.jostudios.penielcommunityx.domain.enums.PermissionsEnum
import id.jostudios.penielcommunityx.presentation.action_activities.member_form.MemberForm
import id.jostudios.penielcommunityx.presentation.ui.theme.PenielCommunityXTheme

@AndroidEntryPoint
class Diakonia : ComponentActivity() {
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
        val context = LocalContext.current;

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
                Text(text = "Diakonia", fontWeight = FontWeight.SemiBold, fontSize = 28.sp);

                if (PermissionManager.checkPermission(PermissionsEnum.EditDiakonia)) {
                    Icon(Icons.Default.Add, contentDescription = "Add Diakonia", modifier = Modifier.clickable {
                        Toast.makeText(context, "Proceeding!", Toast.LENGTH_SHORT).show();
                    });
                }
            }
            Spacer(modifier = Modifier.height(20.dp));

            Column { 
                Text(text = "Diakonia member display...");
            }
        }
    }
}