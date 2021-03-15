package debashis.me.ckycmanager.db

import androidx.annotation.ColorInt
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "kyc_data_table")
data class Kyc(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id : String,
    @ColumnInfo(name = "date")
    val date: Int,
    @ColumnInfo(name = "month")
    val month:Int,
    @ColumnInfo(name = "year")
    val year: Int,
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