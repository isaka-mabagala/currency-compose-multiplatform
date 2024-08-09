package data.api

import domain.CurrencyApiService
import domain.PreferencesRepository
import domain.model.ApiResponse
import domain.model.Currency
import domain.model.CurrencyCode
import domain.model.RequestState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CurrencyApiServiceImpl(
    private val preferencesRepo: PreferencesRepository
) : CurrencyApiService {
    companion object {
        private const val API_KEY = "cur_live_V6oVPhmS6OE9eGAtvsx61lbExvMeGhR3clgifYxo"
        const val ENDPOINT = "https://api.currencyapi.com/v3/latest?apikey=$API_KEY"
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }
        install(DefaultRequest) {
            headers {
                //append("apikey", API_KEY)
            }
        }
    }

    override suspend fun getLatestExchangeRates(): RequestState<List<Currency>> {
        return try {
            val response = httpClient.get(ENDPOINT)

            if (response.status.value == 200) {
                val apiResponse = Json.decodeFromString<ApiResponse>(response.body())

                // filter currency codes
                val availableCodes = apiResponse.data.keys
                    .filter {
                        CurrencyCode.entries
                            .map { code -> code.name }
                            .toSet()
                            .contains(it)
                    }

                val availableCurrencies = apiResponse.data.values
                    .filter { currency ->
                        availableCodes.contains(currency.code)
                    }

                // persist a timestamp
                val lastUpdated = apiResponse.meta.lastUpdatedAt
                preferencesRepo.saveLastUpdated(lastUpdated)

                RequestState.Success(data = availableCurrencies)
            } else {
                RequestState.Error(message = "Http Error Code: ${response.status}")
            }
        } catch (e: Exception) {
            RequestState.Error(message = e.message.toString())
        }
    }
}
