package com.b2cinfosolution.healthgro.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.b2cinfosolution.healthgro.databinding.PopularSurgeryItemBinding
import com.b2cinfosolution.healthgro.databinding.TopHealthItemBinding
import com.b2cinfosolution.healthgro.interfaces.ICallback

class PopularSurgeryAdapter(modelList: List<*>, context: Context?, iCallback: ICallback?) :
    RecyclerView.Adapter<PopularSurgeryAdapter.ViewHolder>() {

    private var iCallback: ICallback? = null
    private val modelList: List<TopHealthPackageModel>
    private val context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate the layout file
        val binding = PopularSurgeryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class ViewHolder(val binding: PopularSurgeryItemBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    init {
        this.modelList = modelList as List<TopHealthPackageModel>
        this.context = context!!
        this.iCallback = iCallback
    }
}
