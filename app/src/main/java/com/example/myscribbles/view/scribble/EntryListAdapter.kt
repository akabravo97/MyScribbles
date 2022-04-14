package com.example.myscribbles.view.scribble

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myscribbles.R
import com.example.myscribbles.model.EntryWithImages
import com.example.myscribbles.utils.TimeUtils
import com.example.myscribbles.utils.getProgressDrawable
import com.example.myscribbles.utils.loadImage
import kotlinx.android.synthetic.main.item_recycler.view.*
import java.util.*

class EntryListAdapter(var entriesWithImages: ArrayList<EntryWithImages>) :
    RecyclerView.Adapter<EntryListAdapter.EntryViewHolder>() {

    class EntryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateMonth = view.date_month
        val contentImage = view.content_image
        val yearDay = view.year_day
        val time = view.time
        val titleEntry = view.title_entry
        val titleContent = view.content
        val c = Calendar.getInstance()
        val progressDrawable = getProgressDrawable(view.context)


        fun bind(entry: EntryWithImages, showDate: Boolean) {
            c.timeInMillis = entry.entry.time

            if (showDate) {
                dateMonth.text = TimeUtils.formatDateMonth(c.time)
                yearDay.text = TimeUtils.formatYearDay(c.time)
            }
            time.text = TimeUtils.formatTime(c.time)
            titleEntry.text = entry.entry.title
            titleContent.text = entry.entry.content

            if (entry.images.size > 0) {
                contentImage.loadImage(entry.images.get(0).imagePath, progressDrawable)
            } else {
                contentImage.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        return EntryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("entryId", entriesWithImages[position].entry.noteId)
            it.findNavController().navigate(R.id.viewScribbleFragment, bundle)
        }
        holder.bind(entriesWithImages[position], true)
    }


    override fun getItemCount(): Int {
        return entriesWithImages.size
    }

    fun updateRecyclerView(newEntryWithImages: List<EntryWithImages>) {
        entriesWithImages.clear()
        entriesWithImages.addAll(newEntryWithImages)
        notifyDataSetChanged()
    }
}