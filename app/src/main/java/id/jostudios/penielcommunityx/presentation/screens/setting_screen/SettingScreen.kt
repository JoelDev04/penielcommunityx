package id.jostudios.penielcommunityx.presentation.screens.setting_screen

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.jostudios.penielcommunityx.common.States
import id.jostudios.penielcommunityx.presentation.action_activities.member_form.MemberForm
import id.jostudios.penielcommunityx.presentation.extras.components.settings.SettingGroupSeparator
import id.jostudios.penielcommunityx.presentation.extras.components.settings.SettingsButton

@Composable
fun SettingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = "Pengaturan",
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        );
        
        Spacer(modifier = Modifier.height(35.dp));

        val editProfileExtras = Intent();
        editProfileExtras.putExtra("uid", States.currentUser.value?.id);

        SettingGroupSeparator(name = "Profiles");
        SettingsButton(text = "Edit Profile", MemberForm::class.java, editProfileExtras);
        SettingsButton(text = "Group Display");
        SettingsButton(text = "Check Permission");

        Spacer(modifier = Modifier.height(25.dp));

        SettingGroupSeparator(name = "System");
        SettingsButton(text = "App Permissions");
        SettingsButton(text = "About App");

        Spacer(modifier = Modifier.height(25.dp));

        SettingGroupSeparator(name = "Privacy");
        SettingsButton(text = "Profile Privacy");
        SettingsButton(text = "Logout");
    }
}