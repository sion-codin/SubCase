package ano.subcase.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ano.subcase.GlobalStatus
import ano.subcase.engine.CaseEngine
import ano.subcase.util.ConfigStore
import ano.subcase.util.NetworkUtil
import ano.subcase.util.NotificationUtil
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics

class SubStoreService : Service() {

    companion object {
        var caseEngine: CaseEngine? = null
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            val frontendPort = 8080
            val backendPort = 8081

            val allowLan = ConfigStore.isAllowLan

            if (NetworkUtil.isPortInUse(frontendPort) || NetworkUtil.isPortInUse(backendPort)) {
                // port is in use
                throw Exception("Port $frontendPort or $backendPort is already in use")
            }
            caseEngine = CaseEngine(
                backendPort = backendPort,
                frontendPort = frontendPort,
                allowLan = allowLan
            )
            caseEngine!!.startServer()

            NotificationUtil.startNotification(this)

            GlobalStatus.isServiceRunning.value = true

            return START_STICKY
        } catch (e: Exception) {
            e.printStackTrace()
            Firebase.crashlytics.recordException(e)
            return START_NOT_STICKY
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        caseEngine!!.stopServer()
        GlobalStatus.isServiceRunning.value = false
        NotificationUtil.stopNotification()
    }
}