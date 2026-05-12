package mn.num.flashstudy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(

    primary = BluePrimary,
    secondary = BlueSecondary,

    background = BackgroundLight,
    surface = CardWhite,

    error = ErrorRed,

    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = androidx.compose.ui.graphics.Color.White,

    onBackground = TextPrimary,
    onSurface = TextPrimary
)

private val DarkColors = darkColorScheme(

    primary = BluePrimary,
    secondary = BlueSecondary
)

@Composable
fun FlashStudyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors =
        if (darkTheme) DarkColors
        else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}