package com.example.uncovid.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.uncovid.DB.DBHelper
import com.example.uncovid.LoginActivity
import com.example.uncovid.R
import com.example.uncovid.ReminderActivity
import com.example.uncovid.SharedViewModel
import com.example.uncovid.databinding.FragmentProfileBinding
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProfileFragment : Fragment() {

    lateinit var dbHelper: DBHelper

    private val sharedViewModel: SharedViewModel by activityViewModels()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val idObserver = Observer<String>{ id ->
            profileName.text = id
        }
        sharedViewModel.currentID.observe(this, idObserver)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DBHelper(requireContext())

        var id = activity?.intent?.getStringExtra("id")
        var name = dbHelper.getUserName(id!!)
        var idCardNo = dbHelper.getUserIDCard(id!!)
        profileName.text = name
        profilePhone.text = id[0] + "" + id[1] + "******" + id[id.length-1]
        profileID.text = idCardNo[0] + "" + idCardNo[1] + "******" + idCardNo[idCardNo.length-1]

        logoutBtn.setOnClickListener {
            // TODO: Need to implement real logout
            val intent = Intent (activity, LoginActivity::class.java)
            intent.putExtra("id", "0")
            activity?.startActivity(intent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}