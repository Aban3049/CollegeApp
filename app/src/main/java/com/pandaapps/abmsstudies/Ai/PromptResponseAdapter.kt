package com.pandaapps.abmsstudies.Ai

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pandaapps.abmsstudies.databinding.RowAiBinding
import android.content.ClipData // for ClipData
import android.content.ClipboardManager



class PromptResponseAdapter(private val items: List<PromptResponsePair>) : RecyclerView.Adapter<PromptResponseAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowAiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pair: PromptResponsePair) {
            binding.promptTextView.text = "Prompt: ${pair.prompt}"
            binding.responseTextView.text = "${pair.response}"

            binding.copyButton.setOnClickListener {
                val clipboardManager = itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("response", pair.response)
                clipboardManager.setPrimaryClip(clipData)

                // Optionally, show a Toast message
                Toast.makeText(itemView.context, "Response copied to clipboard", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowAiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}


