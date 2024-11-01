package id.jostudios.penielcommunityx.common

import java.util.Locale

object TextFormat {
    fun formatCurrency(value: Int): String {
        val formatted = String.format(Locale.US, "%,d", value);
        return formatted;
    }
}