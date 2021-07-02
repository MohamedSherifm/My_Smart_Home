package com.example.mysmarthome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mysmarthome.databinding.OnOffListItemBinding

class OnOffDevicesAdapter : ListAdapter<OnOffDevices, OnOffDevicesAdapter.ViewHolder>(OnOffDevicesCallBack()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: OnOffListItemBinding):RecyclerView.ViewHolder(binding.root)  {
        fun bind(item : OnOffDevices){
            binding.onOffDevice = item
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OnOffListItemBinding.inflate(layoutInflater, parent , false)
                return ViewHolder(binding)
            }
        }

    }
}



class OnOffDevicesCallBack:DiffUtil.ItemCallback<OnOffDevices>() {
    override fun areItemsTheSame(oldItem: OnOffDevices, newItem: OnOffDevices): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: OnOffDevices, newItem: OnOffDevices): Boolean {
        return oldItem == newItem
    }

}
