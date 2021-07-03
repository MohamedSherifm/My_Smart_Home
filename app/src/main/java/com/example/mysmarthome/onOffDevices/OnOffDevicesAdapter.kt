package com.example.mysmarthome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mysmarthome.databinding.ListItemBinding
import com.example.mysmarthome.databinding.OnOffListItemBinding
import com.example.mysmarthome.devicesScreen.DeviceClickListener
import com.example.mysmarthome.devicesScreen.DevicesAdapter
import com.example.mysmarthome.onOffDevices.OnOffDevicesFragment
import com.example.mysmarthome.onOffDevices.OnOffDevicesViewModel
import kotlinx.android.synthetic.main.on_off_list_item.view.*


class OnOffDevicesAdapter(val onClickListener: OnDeviceClickListener, val offClickListener: OffDeviceClickListener)  : ListAdapter<OnOffDevices, OnOffDevicesAdapter.ViewHolder>(OnOffDevicesCallBack()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener, offClickListener)
    }

    class ViewHolder private constructor(val binding: OnOffListItemBinding): RecyclerView.ViewHolder(binding.root)  {
        fun bind(item : OnOffDevices, onClickListener: OnDeviceClickListener,offClickListener: OffDeviceClickListener){
            binding.onOffDevice = item
            binding.onClickListener = onClickListener
            binding.offClickListener = offClickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OnOffListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }




    }
}



class OffDeviceClickListener(val clickListener: (device: OnOffDevices)->Unit) {
    fun onClick(device: OnOffDevices) = clickListener(device)

}

class OnDeviceClickListener(val clickListener: (device: OnOffDevices)->Unit) {
    fun onClick(device: OnOffDevices) = clickListener(device)

}


class OnOffDevicesCallBack:DiffUtil.ItemCallback<OnOffDevices>() {
    override fun areItemsTheSame(oldItem: OnOffDevices, newItem: OnOffDevices): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: OnOffDevices, newItem: OnOffDevices): Boolean {
        return oldItem == newItem
    }



}
