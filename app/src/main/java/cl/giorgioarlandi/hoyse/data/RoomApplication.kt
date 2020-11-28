package cl.giorgioarlandi.hoyse.data

import android.app.Application
import androidx.room.Room
import cl.giorgioarlandi.hoyse.data.models.database.Database
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class RoomApplication : Application() {

    val TAG = "RoomApplication"

    companion object {
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(4)
        var database: Database? = null
    }

    override fun onCreate() {
        super.onCreate()
        database = Room
            .databaseBuilder(
                this,
                Database::class.java,
                "housy-db"
            ).build()
    }
}