package com.example.uncovid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uncovid.R
import com.example.uncovid.RecyclerAdapter
import com.example.uncovid.SharedViewModel
import com.example.uncovid.entity.Answer
import kotlinx.android.synthetic.main.fragment_asks.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AsksFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var layoutManager: RecyclerView.LayoutManager? = null
    //private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    lateinit var adapter: RecyclerAdapter

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
        return inflater.inflate(R.layout.fragment_asks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBox.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        getQuestionList()
    }

    private fun getQuestionList() {
        var questionList = ArrayList<Answer>()
        questionList.add(Answer(0, "What is COVID-19?", "Jan 1, 2021", ""))
        questionList.add(Answer(1, "What causes COVID-19?", "Jan 2, 2021", ""))
        questionList.add(Answer(2, "Where was COVID-19 first discovered?", "Jan 3, 2021", ""))
        questionList.add(Answer(3, "How does COVID-19 spread?", "Jan 4, 2021", ""))
        questionList.add(Answer(4, "What are coronaviruses?", "Jan 5, 2021", ""))
        questionList.add(Answer(5, "How do viruses get their name?", "Jan 6, 2021", ""))
        questionList.add(Answer(6, "In what conditions does COVID-19 survive?", "Jan 7, 2021", ""))
        questionList.add(Answer(7, "When COVID-19 will spread more easily?", "Jan 8, 2021", ""))

        layoutManager = LinearLayoutManager(activity)

        recyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapter(questionList)
        recyclerView.adapter = adapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AsksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AsksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}