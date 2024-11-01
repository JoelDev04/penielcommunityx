package id.jostudios.penielcommunityx.presentation.extras.components.diakonia

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.jostudios.penielcommunityx.domain.model.DiakoniaModel
import id.jostudios.penielcommunityx.presentation.activities.diakonia.DiakoniaViewModel

@Composable
fun LazyDiakoniaColumn(
    map: Map<String, List<DiakoniaModel>>,
    viewModel: DiakoniaViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        map.entries.forEachIndexed { index, entry ->
            val key = entry.key;
            val value = entry.value;
            var totalValue = 0;

            if (key == "100") {
                return@forEachIndexed
            }

            value.forEach {
                totalValue += it.amountPaid!!;
            }

            viewModel.fetchUsername(key);

            item {
                val name = viewModel.getName(key);

                if (name != null) {
                    DiakoniaMember(name = name, amount = totalValue);
                    Spacer(modifier = Modifier.height(10.dp));
                }
            }
        }
    }
}