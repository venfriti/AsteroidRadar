package com.android.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.asteroidradar.R
import com.android.asteroidradar.databinding.FragmentMainBinding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var asteroidAdapter: AsteroidAdapter

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigateToDetailFragment.observe(viewLifecycleOwner, Observer{asteroid ->
            asteroid?.let {
                this.findNavController().navigate(
                    MainFragmentDirections
                        .actionShowDetail(asteroid)
                )
                viewModel.onNavigateToDetailFragment()
            }
        })

        val adapter = AsteroidAdapter(AsteroidListener {
                asteroid -> viewModel.onNavigateClicked(asteroid)
        })
        binding.asteroidRecycler.adapter = adapter

        viewModel.state.onEach { asteroidState ->
            adapter.submitList(asteroidState.asteroids)
        }.launchIn(lifecycleScope)

        viewModel.loadingState.onEach { isLoading ->
            binding.statusLoadingWheel.isVisible = isLoading
        }.launchIn(lifecycleScope)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
