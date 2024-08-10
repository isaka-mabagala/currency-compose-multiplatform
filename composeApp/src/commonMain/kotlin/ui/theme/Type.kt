package ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import currencyapp.composeapp.generated.resources.Res
import currencyapp.composeapp.generated.resources.poppins_black
import currencyapp.composeapp.generated.resources.poppins_black_italic
import currencyapp.composeapp.generated.resources.poppins_bold
import currencyapp.composeapp.generated.resources.poppins_bold_italic
import currencyapp.composeapp.generated.resources.poppins_extra_bold
import currencyapp.composeapp.generated.resources.poppins_extra_bold_italic
import currencyapp.composeapp.generated.resources.poppins_extra_light
import currencyapp.composeapp.generated.resources.poppins_extra_light_italic
import currencyapp.composeapp.generated.resources.poppins_italic
import currencyapp.composeapp.generated.resources.poppins_light
import currencyapp.composeapp.generated.resources.poppins_light_italic
import currencyapp.composeapp.generated.resources.poppins_medium
import currencyapp.composeapp.generated.resources.poppins_medium_italic
import currencyapp.composeapp.generated.resources.poppins_regular
import currencyapp.composeapp.generated.resources.poppins_semi_bold
import currencyapp.composeapp.generated.resources.poppins_semi_bold_italic
import currencyapp.composeapp.generated.resources.poppins_thin
import currencyapp.composeapp.generated.resources.poppins_thin_italic
import org.jetbrains.compose.resources.Font

// Set of Material typography styles to start with
@Composable
private fun PoppinsFontFamily() = FontFamily(
    Font(Res.font.poppins_regular),
    Font(Res.font.poppins_italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(Res.font.poppins_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(Res.font.poppins_bold, FontWeight.Bold),
    Font(Res.font.poppins_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.poppins_black, FontWeight.Black),
    Font(Res.font.poppins_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(Res.font.poppins_extra_bold, FontWeight.ExtraBold),
    Font(Res.font.poppins_extra_bold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(Res.font.poppins_light, FontWeight.Light),
    Font(Res.font.poppins_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.poppins_extra_light, FontWeight.ExtraLight),
    Font(Res.font.poppins_extra_light_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(Res.font.poppins_medium, FontWeight.Medium),
    Font(Res.font.poppins_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(Res.font.poppins_thin, FontWeight.Thin),
    Font(Res.font.poppins_thin_italic, FontWeight.Thin, FontStyle.Italic)
)

@Composable
fun PoppinsTypography() = Typography().run {
    val poppins = PoppinsFontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = poppins),
        displayMedium = displayMedium.copy(fontFamily = poppins),
        displaySmall = displaySmall.copy(fontFamily = poppins),

        headlineLarge = headlineLarge.copy(fontFamily = poppins),
        headlineMedium = headlineMedium.copy(fontFamily = poppins),
        headlineSmall = headlineSmall.copy(fontFamily = poppins),

        titleLarge = titleLarge.copy(fontFamily = poppins),
        titleMedium = titleMedium.copy(fontFamily = poppins),
        titleSmall = titleSmall.copy(fontFamily = poppins),

        bodyLarge = bodyLarge.copy(fontFamily = poppins),
        bodyMedium = bodyMedium.copy(fontFamily = poppins),
        bodySmall = bodySmall.copy(fontFamily = poppins),

        labelLarge = labelLarge.copy(fontFamily = poppins),
        labelMedium = labelMedium.copy(fontFamily = poppins),
        labelSmall = labelSmall.copy(fontFamily = poppins)
    )
}
