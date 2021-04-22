package debashis.me.ckycmanager.data

import androidx.room.TypeConverter
import debashis.me.ckycmanager.adapters.BillStatus
import debashis.me.ckycmanager.db.Bill

class BillStatusConverter {

    @TypeConverter
    fun billStatusToInt(status: BillStatus):Int{
        return when(status){
            BillStatus.NOT_PAID -> -1
            BillStatus.COMPLETE -> 0
            BillStatus.BILL_AVAILABLE -> 1
        }
    }

    @TypeConverter
    fun intToBillStatus(int: Int):BillStatus{
        return when(int){
             -1-> BillStatus.NOT_PAID
             0-> BillStatus.COMPLETE
             1->   BillStatus.BILL_AVAILABLE
            else -> BillStatus.NOT_PAID
        }
    }

}