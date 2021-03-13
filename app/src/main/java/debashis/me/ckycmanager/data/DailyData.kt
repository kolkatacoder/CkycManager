package debashis.me.ckycmanager.data

data class DailyData (
        val date:Dates,
        val scan:Int,
        val cheker:Int,
        val maker:Int,
        val webCheker:Int,
        val webMaker: Int,
        val account:Int
        )