package debashis.me.ckycmanager.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import debashis.me.ckycmanager.adapters.BillStatus
import java.util.*


@Entity(tableName = "bill_table",indices = [Index(value = ["bill_no"],unique = true)])
data class Bill (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val id :Int,

        @ColumnInfo(name = "bill_no") val billNumber : Int,
        @ColumnInfo(name = "start_date") val startDate:Date?,
        @ColumnInfo(name = "end_date") val endDate : Date?,
        @ColumnInfo(name = "issue_date") val issueDate : Date?,
        @ColumnInfo(name = "pass_date") val passDate: Date?,
        @ColumnInfo(name = "total_value") val totalValue: Float,
        @ColumnInfo(name = "bill_path") val billPath : String,
        @ColumnInfo(name = "bill_status") val billStatus: BillStatus

        )