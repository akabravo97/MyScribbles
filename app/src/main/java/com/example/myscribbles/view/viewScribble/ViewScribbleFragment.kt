package com.example.myscribbles.view.viewScribble

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myscribbles.R

class ViewScribbleFragment : Fragment() {
    var entryId: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_scribble_fragment, container, false)
        entryId = requireArguments()["entryId"].toString().toInt()
        println("Entry $entryId")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}