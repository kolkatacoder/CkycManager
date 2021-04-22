package debashis.me.ckycmanager.data


import android.os.Build
import android.provider.SyncStateContract
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DateConverter {

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    @TypeConverter
    fun DateToString(date: Date?):String?{
       return date?.let {
            simpleDateFormat.format(it)
       }
    }

    @TypeConverter
    fun StringToDate(date:String?):Date?{
      return  date?.let {
            simpleDateFormat.parse(date)
        }
    }


}