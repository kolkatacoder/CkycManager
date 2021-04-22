package debashis.me.ckycmanager.db

import androidx.annotation.ColorInt
import androidx.room.*
import debashis.me.ckycmanager.data.DateConverter
import java.util.*


@Entity(tableName = "kyc_data_table",indices = [Index(value = ["date"],unique = true)])
data class Kyc(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int,
    @ColumnInfo(name = "date")
    @TypeConverters(DateConverter::class)
    val date: Date?,
    @ColumnInfo(name = "ckyc")
    val ckyc: Int,
    @ColumnInfo( name = "maker")
    val maker: Int,
    @ColumnInfo( name = "checker")
    val checker: Int,
    @ColumnInfo(name = "web_maker")
    val web_maker: Int,
    @ColumnInfo(name = "web_checker")
    val web_checker: Int,
    @ColumnInfo(name = "account_numbering")
    val account_numbering : Int



)