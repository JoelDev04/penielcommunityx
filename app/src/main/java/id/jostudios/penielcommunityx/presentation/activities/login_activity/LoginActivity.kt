package id.jostudios.penielcommunityx.presentation.activities.login_activity

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.jostudios.penielcommunityx.presentation.LoginActivity
import id.jostudios.penielcommunityx.presentation.MainActivity


@Composable
fun LoginActivity(
    activity: LoginActivity,
    loginViewModel: LoginViewModel
) {
    var counter = remember {
        mutableStateOf(0)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier
            .height(20.dp))

        Text(text = "Login Page",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.clickable {
                counter.value++;
                if (counter.value == 5) {
                    loginViewModel.signup()
                    counter.value = 0;
                }
            }
        );

        Spacer(modifier = Modifier
            .height(50.dp))

        LoginForm(activity, loginViewModel);
    }
}

@Composable
fun LoginForm(
    activity: LoginActivity,
    loginViewModel: LoginViewModel
) {
    var state = loginViewModel.state.value;

    Column(modifier = Modifier
        .padding(horizontal = 20.dp)) {

        Text(text = "Username")
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = state.username, onValueChange = {
            loginViewModel.setUsername(it);
        });
        
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Password")
        TextField(modifier = Modifier
            .fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            value = state.password, onValueChange = {
            loginViewModel.setPassword(it);
        });

        if (state.error != null) {
            Text(text = "Error : ${state.error.toString()}", color = Color.Red)
        }

        Spacer(modifier = Modifier
            .fillMaxHeight()
            .weight(1f))

        Button(modifier = Modifier
            .fillMaxWidth()
            .weight(0.1f),
            onClick = {
                loginViewModel.login();
            }) {
            Text(text = "Login");
        }

        Spacer(modifier = Modifier
            .height(25.dp)
        )

        if (state.isSuccess) {
            Toast.makeText(activity, "Successfully login!", Toast.LENGTH_SHORT).show();

            val mainActivity = Intent(activity, MainActivity::class.java);
            mainActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK;
            activity.startActivity(mainActivity);
            activity.finish();
        }
    }
}