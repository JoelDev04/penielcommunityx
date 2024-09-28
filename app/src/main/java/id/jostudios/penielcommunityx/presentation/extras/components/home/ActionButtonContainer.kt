package id.jostudios.penielcommunityx.presentation.extras.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActionButtonContainer(
    modifier: Modifier,
    count: Int,
    buttons: List<ActionButtonModel>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val fullRows = count / 3
        val remainingButtons = count % 3

        for (i in 0 until fullRows) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                for (j in 0 until 3) {
                    val currentButton = buttons[i * 3 + j]
                    ActionButton(
                        context = currentButton.context,
                        name = currentButton.name,
                        intentClass = currentButton.intentClass
                    )
                }
            }
        }

        // Handle any remaining buttons (less than 3) in a new row
        if (remainingButtons > 0) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                for (j in 0 until remainingButtons) {
                    val currentButton = buttons[fullRows * 3 + j]

                    ActionButton(
                        context = currentButton.context,
                        name = currentButton.name,
                        intentClass = currentButton.intentClass
                    )

                    if (remainingButtons < 3 && j == 1) {
                        Spacer(modifier = Modifier
                            .width(120.dp))
                    }
                }
            }
        }
    }
}