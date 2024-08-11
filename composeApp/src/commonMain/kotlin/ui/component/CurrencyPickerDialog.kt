package ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.Currency
import domain.model.CurrencyCode
import domain.model.CurrencyType
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPickerDialog(
    currencies: List<Currency>,
    currencyType: CurrencyType,
    onSelect: (CurrencyCode) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedCurrency by remember(currencyType) {
        mutableStateOf(currencyType.code)
    }

    BasicAlertDialog(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 20.dp),
        onDismissRequest = onDismiss
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CurrencyPickerTitle("Select a currency")
            AnimatedContent(
                targetState = currencies
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(
                        items = currencies,
                        key = { it._id.toHexString() }
                    ) { currency ->
                        CurrencyPickerRow(
                            code = CurrencyCode.valueOf(currency.code),
                            isSelected = selectedCurrency.name == currency.code,
                            onSelect = { selectedCurrency = it }
                        )
                    }
                }
            }
            CurrencyPickerAction(
                onCancelClick = onDismiss,
                onDoneClick = { onSelect(selectedCurrency) }
            )
        }
    }
}

@Composable
private fun CurrencyPickerTitle(text: String) {
    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = text,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun CurrencyPickerRow(
    code: CurrencyCode,
    isSelected: Boolean,
    onSelect: (CurrencyCode) -> Unit
) {
    val saturation = remember { Animatable(if (isSelected) 1f else 0f) }

    LaunchedEffect(isSelected) {
        saturation.animateTo(if (isSelected) 1f else 0f)
    }

    val colorMatrix = remember(saturation.value) {
        ColorMatrix().apply {
            setToSaturation(saturation.value)
        }
    }

    val animatedAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.5f,
        animationSpec = tween(durationMillis = 300)
    )

    Row(
        modifier = Modifier.clickable { onSelect(code) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(code.flag),
                    colorFilter = ColorFilter.colorMatrix(colorMatrix),
                    contentDescription = "Currency Flag"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    modifier = Modifier.alpha(animatedAlpha),
                    text = code.name,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            CurrencyPickerSelector(isSelected = isSelected)
        }
    }
}

@Composable
private fun CurrencyPickerSelector(isSelected: Boolean = false) {
    val animatedColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.tertiaryContainer
        } else {
            MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f)
        },
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = Modifier
            .size(18.dp)
            .clip(CircleShape)
            .background(animatedColor),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                modifier = Modifier.size(12.dp),
                imageVector = Icons.Default.Check,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "Checkmark Icon"
            )
        }
    }
}

@Composable
private fun CurrencyPickerAction(
    onDoneClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = onCancelClick) {
            Text(
                text = "Cancel",
                color = MaterialTheme.colorScheme.outline
            )
        }
        TextButton(onClick = onDoneClick) {
            Text(
                text = "Confirm",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
