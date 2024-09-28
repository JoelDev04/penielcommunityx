package id.jostudios.penielcommunityx.presentation.ui.theme

import android.app.Activity
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

private val DarkColorScheme = darkColorScheme(
    primary = matchaSage,
    secondary = softLightBlue,
    tertiary = softDarkBlue,
)

private val LightColorScheme = lightColorScheme(
    primary = matchaSage,
    secondary = softLightBlue,
    tertiary = softDarkBlue,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun PenielCommunityXTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    window: Window,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current;
    val colorScheme = when {
        // Use dynamic colors if available and enabled
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme // Fallback to predefined dark color scheme
        else -> LightColorScheme // Fallback to predefined light color scheme
    }

    window.statusBarColor = matchaSage.toArgb();
    window.navigationBarColor = matchaSage.toArgb();

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}