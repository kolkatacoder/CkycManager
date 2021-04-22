package debashis.me.ckycmanager.db

import androidx.lifecycle.LiveData
import androidx.room.*
import debashis.me.ckycmanager.adapters.BillStatus
import java.util.*

@Dao
interface BillDAO {

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(bill:Bill):Long

        @Delete
        suspend fun delete(bill: Bill)

        @Query("SELECT * FROM bill_table")
        fun getAllBills():LiveData<List<Bill>>

        @Query("SELECT * FROM bill_table WHERE bill_no = :billNo ")
        fun getBillByBillNo(billNo: Int):Bill

        @Query("UPDATE bill_table SET bill_path = :path, bill_status = :status WHERE bill_no = :no")
        fun updateBillByBillNoAddPhoto(no: Int,path:String,status: BillStatus)

        @Query("UPDATE bill_table SET pass_date = :date, bill_status=:status WHERE bill_no =:no ")
        fun updateBillByBillNo(no: Int,date: Date, status: BillStatus)
}