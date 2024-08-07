package di

import com.russhwolf.settings.Settings
import data.MongoImpl
import data.PreferencesImpl
import data.api.CurrencyApiServiceImpl
import domain.CurrencyApiService
import domain.MongoRepository
import domain.PreferencesRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ui.screen.HomeViewModel

val appModule = module {
    single { Settings() }
    single<MongoRepository> { MongoImpl() }
    single<PreferencesRepository> { PreferencesImpl(settings = get()) }
    single<CurrencyApiService> { CurrencyApiServiceImpl(preferencesRepo = get()) }

    factory {
        HomeViewModel(
            preferencesRepo = get(),
            mongoDb = get(),
            api = get()
        )
    }
}

fun initializeKoin() {
    startKoin {
        modules(appModule)
    }
}
