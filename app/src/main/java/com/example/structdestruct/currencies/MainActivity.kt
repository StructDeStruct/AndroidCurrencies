package com.example.structdestruct.currencies

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.example.structdestruct.currencies.model.Rate
import com.example.structdestruct.currencies.retrofit.CryptoCompareServiceApi
import kotlinx.android.synthetic.main.fragment_host.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    lateinit var sharedPreferences: SharedPreferences

    lateinit var cryptoCompareServiceApi: CryptoCompareServiceApi

    var fromCurrency: String = "BTC"
    var toCurrency: String = "USD"
    var daysLimit: Int = 1

    fun UpdateRecyclerView(fromCurrency: String, toCurrency: String, daysLimit: Int) {
        val call: Call<Rate> = cryptoCompareServiceApi.GetHistoryDay(fromCurrency, toCurrency, daysLimit-1)

        call.enqueue(object: Callback<Rate> {
            override fun onFailure(call: Call<Rate>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }

            override fun onResponse(call: Call<Rate>, response: Response<Rate>) {
                val exampleList = ArrayList<ItemList>()

                val rate: Rate = response.body()!!
                for (data in rate.Data!!.Data!!) {
                    exampleList += ItemList(data.time!!, data.open!!, fromCurrency, toCurrency, data.low!!, data.high!!)
                }

                recycler_view.adapter = ItemAdapter(exampleList)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val item_id: Int = item.itemId
        if (item_id == R.id.settings_menu_button) {
            val settingsActivity = Intent(this, SettingsActivity::class.java)
            startActivity(settingsActivity)
            return true
        }

        if (item_id == R.id.update_menu_button) {
            fromCurrency = currency_input.text.toString()

            UpdateRecyclerView(fromCurrency, toCurrency, daysLimit)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key.equals("days_number")) {
            Log.i("MainActivity", "days number changed!")

            daysLimit = sharedPreferences?.getString("days_number", "3")!!.toInt()
            // add days change handle
        } else if (key.equals("currency")) {
            Log.i("MainActivity", "currency changed!")

            toCurrency = sharedPreferences?.getString("currency", "USD")!!
            // add currency change handle
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = this.findNavController(R.id.navigation_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("from_currency")) {
                fromCurrency = savedInstanceState.getString("from_currency").toString()
            }
        }

        daysLimit = sharedPreferences.getString("days_number", "3")!!.toInt()
        toCurrency = sharedPreferences.getString("currency", "USD")!!

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val BASE_URL = "https://min-api.cryptocompare.com/"

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()

        cryptoCompareServiceApi = retrofit.create(CryptoCompareServiceApi::class.java)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("from_currency", fromCurrency)
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navigation_fragment)
        return navController.navigateUp()
    }
}