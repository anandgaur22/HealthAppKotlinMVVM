package com.appzdigital.healthgro.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.appzdigital.healthgro.R
import com.appzdigital.healthgro.databinding.FragmentHomeBinding
import com.appzdigital.healthgro.interfaces.ICallback

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewmodel: HomeViewModel
    private var iCallback: ICallback? = null
    private var binding: FragmentHomeBinding? = null
    private var rootView: View? = null
    private val listModels: MutableList<TopHealthPackageModel> = ArrayList()
    private var popularSurgeryAdapter: PopularSurgeryAdapter? = null
    private var topHealthAdapter: TopHealthAdapter? = null


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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewmodel = ViewModelProvider(this).get(HomeViewModel::class.java)
        rootView = binding?.root
        init()
        return rootView
    }

    fun init() {


        binding?.recyclerViewPopular?.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))

        binding?.recyclerTopHealth?.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))


        iCallback = object : ICallback {
            override fun onItemClick(pos: Int) {
                val price_type = listModels[pos].name

            }
        }

        callAdpater()
    }

    fun callAdpater() {

        listModels.add(TopHealthPackageModel("Aannd Sharma"))
        listModels.add(TopHealthPackageModel("Aannd Gaur"))
        listModels.add(TopHealthPackageModel("Aannd Gaur"))
        listModels.add(TopHealthPackageModel("Aannd Gaur"))

        popularSurgeryAdapter = PopularSurgeryAdapter(listModels, context, iCallback)
        topHealthAdapter = TopHealthAdapter(listModels, context, iCallback)

        binding?.recyclerViewPopular?.setAdapter(popularSurgeryAdapter)
        binding?.recyclerTopHealth?.setAdapter(topHealthAdapter)


    }


    companion object {
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