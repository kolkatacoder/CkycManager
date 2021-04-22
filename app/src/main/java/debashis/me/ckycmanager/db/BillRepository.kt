package debashis.me.ckycmanager.db

import androidx.room.TypeConverters
import debashis.me.ckycmanager.adapters.BillStatus
import debashis.me.ckycmanager.data.DateConverter
import java.util.*


class BillRepository(private val billDao: BillDAO) {

    suspend fun addbill(bill: Bill) = billDao.insert(bill)

    suspend fun deleteBill(bill: Bill) = billDao.delete(bill)

    fun getAllBill() = billDao.getAllBills()

    fun getBillByNo(no : Int) = billDao.getBillByBillNo(no)

    fun updateBillByNo(no: Int, date: Date,status: BillStatus) = billDao.updateBillByBillNo(no, date,status)

    fun updateBillAddPhoto(no: Int,path : String, status: BillStatus) = billDao.updateBillByBillNoAddPhoto(no,path,status)





}