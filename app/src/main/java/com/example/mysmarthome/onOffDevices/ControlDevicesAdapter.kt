package com.example.mysmarthome.onOffDevices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mysmarthome.OnOffDevices
import com.example.mysmarthome.databinding.ControlListItemBinding
import com.example.mysmarthome.databinding.OnOffListItemBinding

class ControlDevicesAdapter(val onClickListener: OnDeviceControlClickListener, val offClickListener: OffDeviceControlClickListener,
                          val upClickListener: UpDeviceControlClickListener, val downClickListener: DownDeviceControlClickListener)  : ListAdapter<OnOffDevices, ControlDevicesAdapter.ViewHolder>(OnOffDevicesCallBack()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener, offClickListener,upClickListener,downClickListener)
    }

    class ViewHolder private constructor(val binding: ControlListItemBinding): RecyclerView.ViewHolder(binding.root)  {
        fun bind(item : OnOffDevices, onClickListener: OnDeviceControlClickListener, offClickListener: OffDeviceControlClickListener,
        upClickListener: UpDeviceControlClickListener, downClickListener: DownDeviceControlClickListener){
            binding.onOffDevices = item
            binding.onClickDeviceListener = onClickListener
            binding.offClickDeviceListener = offClickListener
            binding.upClickDeviceListener = upClickListener
            binding.downClickDeviceListener = downClickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ControlListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }




    }
}

class DownDeviceControlClickListener(val clickListener: (device: OnOffDevices) -> Unit) {
    fun onClick(device: OnOffDevices) = clickListener(device)

}

class UpDeviceControlClickListener(val clickListener: (device: OnOffDevices) -> Unit) {
    fun onClick(device: OnOffDevices) = clickListener(device)
}

class OffDeviceControlClickListener(val clickListener: (device: OnOffDevices)->Unit) {
    fun onClick(device: OnOffDevices) = clickListener(device)

}

class OnDeviceControlClickListener(val clickListener: (device: OnOffDevices)->Unit) {
    fun onClick(device: OnOffDevices) = clickListener(device)

}


class OnOffDevicesCallBack: DiffUtil.ItemCallback<OnOffDevices>() {
    override fun areItemsTheSame(oldItem: OnOffDevices, newItem: OnOffDevices): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: OnOffDevices, newItem: OnOffDevices): Boolean {
        return oldItem == newItem
    }



}