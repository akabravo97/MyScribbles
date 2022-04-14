package com.example.myscribbles.view.scribble

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myscribbles.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.scribble_fragment.*

@AndroidEntryPoint
class ScribbleFragment : Fragment() {
    val scribbleViewModel: ScribbleViewModel by viewModels()
    val args: ScribbleFragmentArgs by navArgs()
    val entryAdapter: EntryListAdapter = EntryListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.scribble_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        floating_action_button?.setOnClickListener {
            val action = ScribbleFragmentDirections.actionScribbleFragmentToCreateEntryFragment()
            findNavController().navigate(action)
        }
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = entryAdapter
        }

        scribbleViewModel.getAllEntries().observe(viewLifecycleOwner) {
            for(x in it){
                println("${x.entry.title} has images ${x.images.size}")
            }
            entryAdapter.updateRecyclerView(it)
        }

        scribbleViewModel.failure.observe(viewLifecycleOwner) {

        }
        scribbleViewModel.quote.observe(viewLifecycleOwner) {
            println("Quote : " + it.content)
        }
        scribbleViewModel.getQuote(50)
    }
}