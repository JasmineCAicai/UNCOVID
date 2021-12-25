package com.example.uncovid

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.example.uncovid.databinding.ActivityMainBinding
import com.example.uncovid.entity.DailyCase
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_statistic.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class StatisticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //private lateinit var caseList: ArrayList<String>

    private var arrayList_details: ArrayList<DailyCase> = ArrayList()

    private lateinit var mainHandler: Handler

    val client = OkHttpClient()

    var data = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)

        staBackBtn.setOnClickListener {
            finish()
        }

        progressBar.visibility = View.VISIBLE

        runLineChart("https://api.coronavirus.data.gov.uk/v2/data?areaType=overview&metric=newCasesByPublishDate&format=json")

        runConfirmed("https://api.coronavirus.data.gov.uk/v2/data?areaType=overview&metric=cumCasesByPublishDate&format=json")

        runDeath("https://api.coronavirus.data.gov.uk/v2/data?areaType=overview&metric=cumDeaths28DaysByPublishDate&format=json")
    }

    private fun runConfirmed(url: String) {
        progressBar.visibility = View.VISIBLE
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                progressBar.visibility = View.GONE
            }

            override fun onResponse(call: Call, response: Response) {
                var str_response = response.body()!!.string()
                val json_contact: JSONObject = JSONObject(str_response)
                var jsonarray_info: JSONArray = json_contact.getJSONArray("body")

                var json_objectdetail: JSONObject = jsonarray_info.getJSONObject(0)
                var json_object_prev: JSONObject = jsonarray_info.getJSONObject(1)

                var confirmed = json_objectdetail.getInt("cumCasesByPublishDate")
                var prevConfirmed = json_object_prev.getInt("cumCasesByPublishDate")

                runOnUiThread {
                    casesStatistic.text = confirmed.toString()
                    if (confirmed == prevConfirmed) casesIcon.setImageResource(R.mipmap.icdash)
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun runDeath(url: String) {
        progressBar.visibility = View.VISIBLE
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                progressBar.visibility = View.GONE
            }

            override fun onResponse(call: Call, response: Response) {
                var str_response = response.body()!!.string()
                val json_contact: JSONObject = JSONObject(str_response)
                var jsonarray_info: JSONArray = json_contact.getJSONArray("body")

                var json_objectdetail: JSONObject = jsonarray_info.getJSONObject(0)
                var json_object_prev: JSONObject = jsonarray_info.getJSONObject(1)

                var death = json_objectdetail.getInt("cumDeaths28DaysByPublishDate")
                var prevDeath = json_object_prev.getInt("cumDeaths28DaysByPublishDate")

                runOnUiThread {
                    deathsStatistics.text = death.toString()
                    if (death == prevDeath) deathsIcon.setImageResource(R.mipmap.icdash)
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun runLineChart(url: String) {
        progressBar.visibility = View.VISIBLE
        val request = Request.Builder().url(url).build()

        var series = LineGraphSeries<DataPoint>()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                progressBar.visibility = View.GONE
            }

            override fun onResponse(call: Call, response: Response) {
                var str_response = response.body()!!.string()
                val json_contact: JSONObject = JSONObject(str_response)
                var jsonarray_info: JSONArray = json_contact.getJSONArray("body")
                var size: Int = jsonarray_info.length()
                arrayList_details = ArrayList()
                for (i in 0..size-1) {
                    var json_objectdetail: JSONObject = jsonarray_info.getJSONObject(i)
                    var case = DailyCase(LocalDate.parse(json_objectdetail.getString("date"), DateTimeFormatter.ISO_DATE), json_objectdetail.getInt("newCasesByPublishDate").toDouble())
                    arrayList_details.add(case)
                }

                runOnUiThread {
                    for (i in size-1 downTo 0 step 1) {
                        var date = Date.from(arrayList_details[i].date.atStartOfDay(ZoneId.systemDefault()).toInstant())
                        series.appendData(DataPoint(date, arrayList_details[i].num), false, size)
                    }
                    series.thickness = 5
                    series.color = resources.getColor(R.color.text_3)
                    lineChart.title = "Daily Cases"
                    lineChart.setBackgroundColor(resources.getColor(R.color.background_3))

                    lineChart.addSeries(series)
                    progressBar.visibility = View.GONE
                }
            }
        })

    }
}