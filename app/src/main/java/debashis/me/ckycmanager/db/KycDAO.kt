package debashis.me.ckycmanager.db

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface KycDAO  {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(kyc: Kyc): Long

    @Query("SELECT * FROM kyc_data_table WHERE strftime('%m', date) = :month ORDER BY Date(date) DESC")
    fun getDataByMonth(month:String):LiveData<List<Kyc>>

    @Query("SELECT * FROM kyc_data_table ORDER BY DATE(date) LIMIT :limit")
    fun getDataByDay(limit: Int):List<Kyc>

    @Query("SELECT * FROM kyc_data_table WHERE date > Date(:date) ORDER BY DATE(date) LIMIT :limit")
    fun getDataByDay(limit: Int, date: String):List<Kyc>

    @Query("SELECT DISTINCT strftime('%m %Y',date) FROM KYC_DATA_TABLE ORDER BY Date(date) DESC")
    fun allMonth():LiveData<List<String>>

    @Delete()
    suspend fun delete(kyc: Kyc)


}