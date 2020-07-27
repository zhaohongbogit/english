package tk.hb.english

import android.app.Application
import android.content.Context

/**
 * Created by HONGBO on 2020/7/27 19:40
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        application = applicationContext
    }

    companion object {

        private lateinit var application: Context

        fun getAppContext(): Context {
            return application
        }
    }
}