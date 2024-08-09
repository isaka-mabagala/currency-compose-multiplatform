package domain.model

import currencyapp.composeapp.generated.resources.Res
import currencyapp.composeapp.generated.resources.bif
import currencyapp.composeapp.generated.resources.cny
import currencyapp.composeapp.generated.resources.eur
import currencyapp.composeapp.generated.resources.gbp
import currencyapp.composeapp.generated.resources.jpy
import currencyapp.composeapp.generated.resources.kes
import currencyapp.composeapp.generated.resources.rwf
import currencyapp.composeapp.generated.resources.tzs
import currencyapp.composeapp.generated.resources.ugx
import currencyapp.composeapp.generated.resources.usd
import currencyapp.composeapp.generated.resources.zar
import org.jetbrains.compose.resources.DrawableResource

enum class CurrencyCode(
    val country: String,
    val flag: DrawableResource
) {
    BIF(country = "Burundi", flag = Res.drawable.bif),
    CNY(country = "China", flag = Res.drawable.cny),
    EUR(country = "Euro", flag = Res.drawable.eur),
    GBP(country = "United Kingdom", flag = Res.drawable.gbp),
    JPY(country = "Japan", flag = Res.drawable.jpy),
    KES(country = "Kenya", flag = Res.drawable.kes),
    RWF(country = "Rwanda", flag = Res.drawable.rwf),
    TZS(country = "Tanzania", flag = Res.drawable.tzs),
    UGX(country = "Uganda", flag = Res.drawable.ugx),
    USD(country = "USA", flag = Res.drawable.usd),
    ZAR(country = "South Africa", flag = Res.drawable.zar),
}
