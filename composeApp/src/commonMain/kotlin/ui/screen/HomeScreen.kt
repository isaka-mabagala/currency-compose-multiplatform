package ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import ui.component.HomeHeader

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<HomeViewModel>()
        val rateStatus = viewModel.rateStatus
        val sourceCurrency = viewModel.sourceCurrency
        val targetCurrency = viewModel.targetCurrency

        var amount by rememberSaveable { mutableStateOf(0.0) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeHeader(
                status = rateStatus,
                source = sourceCurrency,
                target = targetCurrency,
                amount = amount,
                onAmountChange = { amount = it },
                onRatesRefresh = {
                    viewModel.sendEvent(HomeUiEvent.RefreshRates)
                },
                onSwitchClick = {
                    viewModel.sendEvent(HomeUiEvent.SwitchCurrencies)
                }
            )
        }
    }
}
