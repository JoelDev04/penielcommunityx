package id.jostudios.penielcommunityx.presentation.extras.components.member_form

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FormTextInput(
    label: String,
    value: String,
    isStatic: Boolean = false,
    onChange: (value: String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 16.sp);
        Spacer(modifier = Modifier.width(40.dp));
        Text(text = ":", fontSize = 16.sp);
        Spacer(modifier = Modifier.width(10.dp));
        TextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = isStatic,
            value = value,
            onValueChange = onChange,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 12.sp
            )
        );
    }
}