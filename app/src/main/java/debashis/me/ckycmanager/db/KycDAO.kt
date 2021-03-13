package debashis.me.ckycmanager.db

import androidx.room.Insert
import androidx.room.Query

interface KycDAO {

    @Insert
    suspend fun insert(kyc: Kyc)

    @Query("SELECT * FROM kyc_data_table")
    fun getMonthlyData()
    


}