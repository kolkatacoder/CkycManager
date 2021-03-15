package debashis.me.ckycmanager.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Kyc::class],version = 2, exportSchema = false)
abstract class KycDatabase: RoomDatabase() {

   abstract val kycDao : KycDAO

    companion object{

        @Volatile
        private var Instance : KycDatabase? = null

        fun getDatabase(context: Context):KycDatabase?{

            synchronized(this){
                var instance:KycDatabase? = Instance
                if (Instance == null){
                     instance = Room.databaseBuilder(
                            context.applicationContext,
                            KycDatabase::class.java,
                            "kyc_database"
                    ).build()
                Instance = instance
                }

                return instance
            }
        }
    }


}