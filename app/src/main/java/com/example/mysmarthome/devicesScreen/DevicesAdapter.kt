package com.example.mysmarthome.devicesScreen


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mysmarthome.databinding.ListItemBinding

class DevicesAdapter(val clickListener: DeviceClickListener) : ListAdapter<Devices, DevicesAdapter.ViewHolder>(DevicesDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Devices, clickListener: DeviceClickListener){
            binding.devices = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}


class DeviceClickListener(val clickListener: (device: Devices)-> Unit) {
    fun onClick(device: Devices) = clickListener(device)

}

class DevicesDiffCallBack: DiffUtil.ItemCallback<Devices>() {
    override fun areItemsTheSame(oldItem: Devices, newItem: Devices): Boolean {
        return oldItem.deviceId == newItem.deviceId
    }



    override fun areContentsTheSame(oldItem: Devices, newItem: Devices): Boolean {
        return oldItem == newItem
    }

}
