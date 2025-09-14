package ano.subcase.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ano.subcase.GlobalStatus
import ano.subcase.ui.theme.SubCaseTheme
import ano.subcase.util.NetworkUtil
import ano.subcase.util.NotificationUtil

lateinit var caseActivity: MainActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        caseActivity = this

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            SubCaseTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }

        GlobalStatus.lanIP.value = NetworkUtil.getLanIp() ?: ""

        // prepare notification
        NotificationUtil.checkAndRequestPermission()

        // prepare network
        NetworkUtil.startObserve()
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkUtil.stopObserve()
    }

}

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") { MainScreen(navController = navController) }
    }
}