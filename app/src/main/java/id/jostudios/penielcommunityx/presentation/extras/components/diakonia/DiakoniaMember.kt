package id.jostudios.penielcommunityx.presentation.extras.components.diakonia

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.jostudios.penielcommunityx.common.TextFormat
import id.jostudios.penielcommunityx.domain.model.UserModel
import id.jostudios.penielcommunityx.presentation.MemberDetailsActivity

@Composable
fun DiakoniaMember(
    name: String,
    amount: Int
) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20f)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 25.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "@${name}");
            Text(text = "Total : Rp ${TextFormat.formatCurrency(amount)}")
        }
    }
}