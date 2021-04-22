package debashis.me.ckycmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)



        title_text.animate().apply {
            translationYBy(-200f)
            duration = 1000
            startDelay = 200
        }





        Handler().postDelayed({
            title_text.animate().apply {
                alphaBy(100f)
                duration = 500
                startDelay = 0
            }
            Intent(this@SplashScreenActivity,MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }, 4200)


    }
}