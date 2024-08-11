import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import di.initializeKoin
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.screen.HomeScreen
import ui.theme.AppTheme

@Composable
@Preview
fun App() {
    initializeKoin()

    AppTheme {
        Navigator(HomeScreen())
    }
}
