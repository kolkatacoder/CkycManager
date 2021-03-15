package debashis.me.ckycmanager.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.Job

@Dao
interface KycDAO  {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(kyc: Kyc)

    @Query("SELECT * FROM kyc_data_table WHERE month = :month")
    fun getDataByMonth(month:Int):LiveData<List<Kyc>>
    


}