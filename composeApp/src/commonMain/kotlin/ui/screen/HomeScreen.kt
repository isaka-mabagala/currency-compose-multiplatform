package ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import domain.model.CurrencyType
import ui.component.CurrencyPickerDialog
import ui.component.HomeBody
import ui.component.HomeHeader

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<HomeViewModel>()
        val rateStatus = viewModel.rateStatus
        val sourceCurrency = viewModel.sourceCurrency
        val targetCurrency = viewModel.targetCurrency
        val allCurrencies = viewModel.allCurrencies

        var amount by rememberSaveable { mutableStateOf(0.0) }

        var selectedCurrencyType: CurrencyType by remember {
            mutableStateOf(CurrencyType.None)
        }
        var dialogOpened by remember { mutableStateOf(false) }

        if (dialogOpened && selectedCurrencyType != CurrencyType.None) {
            CurrencyPickerDialog(
                currencies = allCurrencies,
                currencyType = selectedCurrencyType,
                onDismiss = {
                    // reset dialog data
                    selectedCurrencyType = CurrencyType.None
                    dialogOpened = false
                },
                onSelect = { currencyCode ->
                    // save new currency code
                    if (selectedCurrencyType is CurrencyType.Source) {
                        viewModel.sendEvent(
                            HomeUiEvent.SaveSourceCurrencyCode(currencyCode.name)
                        )
                    } else if (selectedCurrencyType is CurrencyType.Target) {
                        viewModel.sendEvent(
                            HomeUiEvent.SaveTargetCurrencyCode(currencyCode.name)
                        )
                    }

                    // reset dialog data
                    selectedCurrencyType = CurrencyType.None
                    dialogOpened = false
                }
            )
        }

        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        // hide keyboard and clear focus
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                    })
                }
        ) {
            HomeHeader(
                status = rateStatus,
                source = sourceCurrency,
                target = targetCurrency,
                amount = amount,
                onAmountChange = { amount = it },
                onRatesRefresh = {
                    // fetch and save new currencies
                    viewModel.sendEvent(HomeUiEvent.RefreshRates)
                },
                onSwitchClick = {
                    // switch source and target currencies
                    viewModel.sendEvent(HomeUiEvent.SwitchCurrencies)
                },
                onCurrencyTypeSelect = { currencyType ->
                    // open dialog based on source and target currency type
                    selectedCurrencyType = currencyType
                    dialogOpened = true
                }
            )
            HomeBody(
                source = sourceCurrency,
                target = targetCurrency,
                amount = amount
            )
        }
    }
}
