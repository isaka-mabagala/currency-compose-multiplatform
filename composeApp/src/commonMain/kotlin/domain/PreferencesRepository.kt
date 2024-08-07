package domain

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun saveLastUpdated(lastUpdated: String)
    suspend fun isDataFresh(currentTimeStamp: Long): Boolean
    suspend fun saveSourceCurrencyCode(code: String)
    suspend fun saveTargetCurrencyCode(code: String)
    fun readSourceCurrencyCode(): Flow<String>
    fun readTargetCurrencyCode(): Flow<String>
}
