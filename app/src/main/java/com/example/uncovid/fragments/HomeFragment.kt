package com.example.uncovid.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.uncovid.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.uncovid.DB.DBHelper
import com.example.uncovid.entity.Cases
import com.example.uncovid.lifecycle.ResourceHandler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_statistic.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {

    lateinit var dbHelper: DBHelper

    private val resourceHandler: ResourceHandler = ResourceHandler()

    //private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: SharedViewModel

    val client = OkHttpClient()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DBHelper(requireContext())

        var id = activity?.intent?.getStringExtra("id")
        var name = dbHelper.getUserName(id!!)
        hiText2.text = "Hi, " + name + " 👋"


        var urlCases = "https://api.coronavirus.data.gov.uk/v2/data?areaType=overview&metric=cumCasesByPublishDate&format=json"
        val requestCases = Request.Builder().url(urlCases).build()

        client.newCall(requestCases).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(context, "Failure to fetch confirmed cases data", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call, response: Response) {
                var strResponse = response.body()!!.string()
                val jsonContact: JSONObject = JSONObject(strResponse)
                var jsonarrayInfo: JSONArray = jsonContact.getJSONArray("body")

                var jsonObjectDetail: JSONObject = jsonarrayInfo.getJSONObject(0)
                var jsonObjectPrev: JSONObject = jsonarrayInfo.getJSONObject(1)

                var confirmed = jsonObjectDetail.getInt("cumCasesByPublishDate")
                var prevConfirmed = jsonObjectPrev.getInt("cumCasesByPublishDate")

                activity?.runOnUiThread {
                    casesHome.text = confirmed.toString()
                    if (confirmed == prevConfirmed) casesHomeIcon.setImageResource(R.mipmap.icdash)
                }
            }
        })


        var urlDeath = "https://api.coronavirus.data.gov.uk/v2/data?areaType=overview&metric=cumDeaths28DaysByPublishDate&format=json"
        val requestDeath = Request.Builder().url(urlDeath).build()

        client.newCall(requestDeath).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(context, "Failure to fetch death cases data", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call, response: Response) {
                var strResponse = response.body()!!.string()
                val jsonContact: JSONObject = JSONObject(strResponse)
                var jsonarrayInfo: JSONArray = jsonContact.getJSONArray("body")

                var jsonObjectDetail: JSONObject = jsonarrayInfo.getJSONObject(0)
                var jsonObjectPrev: JSONObject = jsonarrayInfo.getJSONObject(1)

                var death = jsonObjectDetail.getInt("cumDeaths28DaysByPublishDate")
                var prevDeath = jsonObjectPrev.getInt("cumDeaths28DaysByPublishDate")

                activity?.runOnUiThread {
                    deathsHome.text = death.toString()
                    if (death == prevDeath) deathsHomeIcon.setImageResource(R.mipmap.icdash)
                }
            }
        })


        reminderHomeBtn.setOnClickListener {
            val intent = Intent (activity, ReminderActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
        faqHomeBtn.setOnClickListener {
            (activity as MainActivity?)!!.bottom_navigation.selectedItemId = R.id.ic_question_answer
            (activity as MainActivity?)!!.makeCurrentFragment(AsksFragment())
        }
        scannerHomeBtn.setOnClickListener {
            val intent = Intent (activity, ScannerActivity::class.java)
            activity?.startActivity(intent)
        }
        historyHomeBtn.setOnClickListener {
            val intent = Intent (activity, LocationListActivity::class.java)
            activity?.startActivity(intent)
        }
        statisticHomeBtn.setOnClickListener {
            val intent = Intent (activity, StatisticActivity::class.java)
            activity?.startActivity(intent)
        }
        none.setOnClickListener {
            val intent = Intent (activity, LocationDetailActivity::class.java)
            activity?.startActivity(intent)
        }

        homeCard1.setOnClickListener {
            val intent = Intent(activity, ReminderDetailActivity::class.java)
            intent.putExtra("option", 1)
            startActivity(intent)
        }
        homeCard2.setOnClickListener {
            val intent = Intent(activity, ReminderDetailActivity::class.java)
            intent.putExtra("option", 2)
            startActivity(intent)
        }
        homeCard3.setOnClickListener {
            val intent = Intent(activity, ReminderDetailActivity::class.java)
            intent.putExtra("option", 3)
            startActivity(intent)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}