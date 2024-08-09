package ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import currencyapp.composeapp.generated.resources.Res
import currencyapp.composeapp.generated.resources.ic_currency_exchange
import currencyapp.composeapp.generated.resources.ic_refresh
import currencyapp.composeapp.generated.resources.ic_switch_right
import domain.model.Currency
import domain.model.CurrencyCode
import domain.model.DisplayResult
import domain.model.RateStatus
import domain.model.RequestState
import org.jetbrains.compose.resources.painterResource
import util.displayCurrentDateTime

@Composable
fun HomeHeader(
    status: RateStatus,
    source: RequestState<Currency>,
    target: RequestState<Currency>,
    amount: Double,
    onRatesRefresh: () -> Unit,
    onSwitchClick: () -> Unit,
    onAmountChange: (Double) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(all = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        RatesStatus(
            status = status,
            onRatesRefresh = onRatesRefresh
        )
        Spacer(modifier = Modifier.height(24.dp))
        CurrencyInputs(
            source = source,
            target = target,
            onSwitchClick = onSwitchClick
        )
        Spacer(modifier = Modifier.height(24.dp))
        AmountInput(
            amount = amount,
            onAmountChange = onAmountChange
        )
    }
}

@Composable
fun RatesStatus(
    status: RateStatus,
    onRatesRefresh: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                modifier = Modifier.size(50.dp),
                painter = painterResource(Res.drawable.ic_currency_exchange),
                contentDescription = "App Logo"
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = displayCurrentDateTime(),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = status.title,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        if (status == RateStatus.Stale) {
            IconButton(onClick = onRatesRefresh) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(Res.drawable.ic_refresh),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    contentDescription = "Refresh Icon"
                )
            }
        }
    }
}

@Composable
fun CurrencyInputs(
    source: RequestState<Currency>,
    target: RequestState<Currency>,
    onSwitchClick: () -> Unit
) {
    // animate switch currency icon on clicked
    var animationStarted by remember { mutableStateOf(false) }
    val animateRotation by animateFloatAsState(
        targetValue = if (animationStarted) 180f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CurrencyView(
            placeholder = "From",
            currency = source,
            onClick = {}
        )
        Spacer(modifier = Modifier.width(14.dp))

        IconButton(
            modifier = Modifier
                .padding(top = 24.dp)
                .graphicsLayer { rotationY = animateRotation },
            onClick = {
                animationStarted = !animationStarted
                onSwitchClick()
            }
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(Res.drawable.ic_switch_right),
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                contentDescription = "Switch Icon"
            )
        }

        Spacer(modifier = Modifier.width(14.dp))
        CurrencyView(
            placeholder = "To",
            currency = target,
            onClick = {}
        )
    }
}

@Composable
fun RowScope.CurrencyView(
    placeholder: String,
    currency: RequestState<Currency>,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.weight(1f)) {
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = placeholder,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(size = 8.dp))
                .background(Color.White.copy(alpha = 0.3f))
                .height(54.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            currency.DisplayResult(
                onSuccess = { data ->
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(CurrencyCode.valueOf(data.code).flag),
                        tint = Color.Unspecified,
                        contentDescription = "Country Flag"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = data.code,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = Color.White
                    )
                }
            )
        }
    }
}

@Composable
fun AmountInput(
    amount: Double,
    onAmountChange: (Double) -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 8.dp))
            .animateContentSize()
            .height(54.dp),
        value = "$amount",
        onValueChange = { onAmountChange(it.toDouble()) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White.copy(alpha = 0.3f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.3f),
            disabledContainerColor = Color.White.copy(alpha = 0.3f),
            errorContainerColor = Color.White.copy(alpha = 0.3f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onTertiaryContainer
        ),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )
    )
}
