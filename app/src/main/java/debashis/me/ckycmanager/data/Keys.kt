package debashis.me.ckycmanager.data

import debashis.me.ckycmanager.db.Kyc

object Keys {
    const val CUSTOME_DATE="custome_date"
    const val DAY = "day"
    const val MONTH = "month"
    const val YEAR = "year"
    val datatype = arrayOf("Scan","web Maker","Web Checker","Maker","Checker","a/c Number")

     fun getMonthName(month:Int)= when(month){
        1->"Jan"
        2->"Feb"
        3->"March"
        4->"April"
        5->"May"
        6->"June"
        7->"Julay"
        8->"Aug"
        9->"Sep"
        10->"Oct"
        11->"Nov"
        12->"Dec"
        else ->""
    }


    fun getTotalMoney(kyc: Kyc):Double{
        return (kyc.ckyc*4)+(kyc.checker*1.33)+(kyc.maker*1.33)+(kyc.web_checker*0.66)+(kyc.web_maker*1.33)+(kyc.account_numbering*1.33)
    }

}