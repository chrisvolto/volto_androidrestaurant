package fr.isen.volto.androiderestaurant.index

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import fr.isen.volto.androiderestaurant.APIManager.APIManager
import fr.isen.volto.androiderestaurant.Category
import fr.isen.volto.androiderestaurant.home.HomeActivity
import fr.isen.volto.androiderestaurant.databinding.IndexActivityBinding
import kotlin.system.exitProcess


class IndexActivity : AppCompatActivity() {
    private lateinit var binding: IndexActivityBinding
    private lateinit var mReqQueue: RequestQueue
    private lateinit var apiManager: APIManager
    private var categories: Array<Category>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = IndexActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mReqQueue = Volley.newRequestQueue(this)
        apiManager = APIManager(mReqQueue) {
            mReqQueue.cancelAll {true}
            Log.i("intent", "rep")
            if( it is Array<*>)
            {
                Log.i("intent", "ici")
                categories = it as? Array<Category>
            }
        }

        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val intent = Intent(this, HomeActivity::class.java)

        object : CountDownTimer(Long.MAX_VALUE, 1000) {

            override fun onFinish() {
                exitProcess(1)
            }

            override fun onTick(millisUntilFinished: Long) {

                val isConnected = cm.activeNetworkInfo?.isConnectedOrConnecting == true
                if(isConnected){
                    binding.labelInternetErrorIndex.visibility = View.INVISIBLE

                    if(categories.isNullOrEmpty()){
                        apiManager.getAllCategories()
                    }
                    else{
                        this.cancel()
                        intent.putExtra("categories", categories)
                        Log.i("intent", "ok")
                        startActivity(intent);
                    }
                }
                else{
                    binding.labelInternetErrorIndex.visibility = View.VISIBLE
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("debug", " destroyed") // log the destroy cycle
    }
}