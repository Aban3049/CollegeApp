package com.pandaapps.abmsstudies.books.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.pandaapps.abmsstudies.books.MyApplication
import com.pandaapps.abmsstudies.books.activities.PdfDetailActivity
import com.pandaapps.abmsstudies.books.filter.FilterPdfUser
import com.pandaapps.abmsstudies.books.model.ModelBookPdf
import com.pandaapps.abmsstudies.databinding.RowPdfUserBinding


class AdapterPdfUser(
    private val context: Context,
    var pdfArrayList: ArrayList<ModelBookPdf>
) : RecyclerView.Adapter<AdapterPdfUser.HolderPdfUser>(), Filterable {

    private var filteredList: ArrayList<ModelBookPdf> = pdfArrayList

    private lateinit var filter: FilterPdfUser

    // ... (other methods)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderPdfUser {
        val binding = RowPdfUserBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderPdfUser(binding)
    }

    override fun onBindViewHolder(holder: HolderPdfUser, position: Int) {
        val model = pdfArrayList[position]
        with(holder.binding) {
            titleTv.text = model.title
            descriptionTv.text = model.description
            dateTv.text = MyApplication.formatTimestampDate(model.timestamp)
            MyApplication.loadBookImage(model.imageUrl, progressBar, context, booksImageIv)
            MyApplication.loadCategory(model.categoryId, categoryTv)
            MyApplication.loadPdfSize(model.url, model.title, sizeTv)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PdfDetailActivity::class.java).apply {
                putExtra("bookId", model.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = pdfArrayList.size


    override fun getFilter(): Filter {
        if (!::filter.isInitialized) {
            filter = FilterPdfUser(filteredList, this)
        }
        return filter
    }

    inner class HolderPdfUser(val binding: RowPdfUserBinding) :
        RecyclerView.ViewHolder(binding.root)
}