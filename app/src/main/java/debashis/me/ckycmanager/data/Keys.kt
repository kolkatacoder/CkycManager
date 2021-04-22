package debashis.me.ckycmanager.data

import android.content.Context
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import debashis.me.ckycmanager.db.Kyc
import debashis.me.ckycmanager.viewModel.dataStore
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.*

fun Date.toDayMonth(): String {
    val sm = SimpleDateFormat("dd MMM")

    return sm.format(this)
}

fun String.toMonthIndex(): String {
    return this.substring(0, 2)
}

fun listToMonthIndexList(list: List<String>): List<String> {
    var tempList = mutableListOf<String>()
    list.forEach {
        tempList.add(Keys.getMonthName(it.toMonthIndex()))

    }
    return tempList
}


object Keys {

    /**Preference Keys*/
    val scan_key = stringPreferencesKey("scan")
    val maker_key = stringPreferencesKey("maker")
    val checker_key = stringPreferencesKey("checker")
    val web_maker_key = stringPreferencesKey("web_maker")
    val web_checker_key = stringPreferencesKey("web_checker")
    val account_number_key = stringPreferencesKey("account_number")
    val last_billno_key = stringPreferencesKey("last_billNo")


    const val CUSTOME_DATE = "custome_date"
    const val DAY = "day"
    const val MONTH = "month"
    const val YEAR = "year"
    val datatype = arrayOf("Scan", "web Maker", "Web Checker", "Maker", "Checker", "a/c Number")


    fun getMonthName(month: String) = when (month.toInt()) {
        1 -> "Jan"
        2 -> "Feb"
        3 -> "Mar"
        4 -> "Apr"
        5 -> "May"
        6 -> "Jun"
        7 -> "Jul"
        8 -> "Aug"
        9 -> "Sep"
        10 -> "Oct"
        11 -> "Nov"
        12 -> "Dec"
        else -> ""
    }


    suspend fun getTotalMoney(kyc: Kyc, context: Context): Float {

        var rupee: Float = 0f
        var result =
        GlobalScope.async{

            val preferences = context.dataStore.data.first()
            val r =  async { (kyc.ckyc * (preferences[scan_key]?.toInt() ?: 0)) +
                    (kyc.checker * (preferences[checker_key]?.toFloat() ?: 0f)) +
                    (kyc.maker * (preferences[maker_key]?.toFloat() ?: 0f)) +
                    (kyc.web_checker * (preferences[web_checker_key]?.toFloat() ?: 0f)) +
                    (kyc.web_maker * (preferences[web_maker_key]?.toFloat() ?: 0f)) +
                    (kyc.account_numbering * (preferences[account_number_key]?.toFloat() ?: 0f)) }


            return@async r.await()
        }

        return "%.2f".format(result.await()).toFloat()
    }

    fun getMonthlyMoney(kyc: List<Kyc>, context: Context): Float {
        var money: Float = 0f
        kyc.forEach {
            runBlocking {

            money += getTotalMoney(it, context)
            }
        }
        return "%.2f".format(money).toFloat()
    }


    fun genPreferenceKey(key: String) = stringPreferencesKey(key)


}


