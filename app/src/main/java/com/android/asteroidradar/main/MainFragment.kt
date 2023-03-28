package com.android.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.asteroidradar.R
import com.android.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val adapter = AsteroidAdapter(AsteroidListener {
            asteroid -> viewModel.onNavigateClicked(asteroid)
        })
        binding.asteroidRecycler.adapter = adapter

        viewModel.navigateToDetailFragment.observe(viewLifecycleOwner, Observer{asteroid ->
            asteroid?.let {
                this.findNavController().navigate(
                    MainFragmentDirections
                        .actionShowDetail(asteroid)
                )
                viewModel.onNavigateToDetailFragment()
            }
        })

        viewModel.response.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }


        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId){
                R.id.show_today -> ApiFilter.SHOW_DAY
                R.id.show_week -> ApiFilter.SHOW_WEEK
                else -> ApiFilter.SHOW_ALL
            }
        )
        return true
    }
}
