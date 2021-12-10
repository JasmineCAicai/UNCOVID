package com.example.uncovid.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.uncovid.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.uncovid.lifecycle.ResourceHandler


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {

    private val resourceHandler: ResourceHandler = ResourceHandler()

    //private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: SharedViewModel

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //println("fragment id: " + sharedViewModel.currentID.value)
        /*
        val idObserver = Observer<String>{ id ->
            hiText2.text = "Hi " + id + " 👋"
            println("observe id: " + sharedViewModel.currentID.value)
            sharedViewModel.currentID.setValue(id)
        }
        sharedViewModel.currentID.observe(this, idObserver)

         */
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

        //sharedViewModel.currentID.observe(viewLifecycleOwner, Observer<String> { id ->
        //    hiText2.text = "Hi, " + id + " 👋"
        //})


        //lifecycle.addObserver(resourceHandler)

        reminderHomeBtn.setOnClickListener {
            val intent = Intent (activity, ReminderActivity::class.java)
            activity?.startActivity(intent)
        }
        faqHomeBtn.setOnClickListener {
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

        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        viewModel.currentID.observe(viewLifecycleOwner, { id ->
            hiText2.text = "Hi, " + id + " 👋"
        })
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