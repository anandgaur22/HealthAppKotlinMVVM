package com.appzdigital.healthgro.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appzdigital.healthgro.databinding.TopHealthItemBinding
import com.appzdigital.healthgro.interfaces.ICallback

class TopHealthAdapter(modelList: List<*>, context: Context?, iCallback: ICallback?) :
    RecyclerView.Adapter<TopHealthAdapter.ViewHolder>() {

    private var iCallback: ICallback? = null
    private val modelList: List<TopHealthPackageModel>
    private val context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate the layout file
        val binding = TopHealthItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)

    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val name = modelList[position].name

        //holder.binding.nametv.text = name

        holder.binding.mrpPrice.setPaintFlags(holder.binding.mrpPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

        holder.binding.cardView.setOnClickListener {
            iCallback?.onItemClick(position)
        }



    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    inner class ViewHolder(val binding: TopHealthItemBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    init {
        this.modelList = modelList as List<TopHealthPackageModel>
        this.context = context!!
        this.iCallback = iCallback
    }
}
