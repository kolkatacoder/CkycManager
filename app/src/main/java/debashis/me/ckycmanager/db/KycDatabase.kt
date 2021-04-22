package debashis.me.ckycmanager.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import debashis.me.ckycmanager.data.BillStatusConverter
import debashis.me.ckycmanager.data.DateConverter


@Database(entities = [Kyc::class,Bill::class],version = 2, exportSchema = false)
@TypeConverters(DateConverter::class,BillStatusConverter::class)

abstract class KycDatabase: RoomDatabase() {

   abstract val kycDao : KycDAO
   abstract val billDao: BillDAO
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