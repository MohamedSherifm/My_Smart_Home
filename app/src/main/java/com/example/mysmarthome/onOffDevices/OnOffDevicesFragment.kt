package com.example.mysmarthome.onOffDevices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mysmarthome.*
import com.example.mysmarthome.databinding.FragmentOnOffDevicesBinding
import com.example.mysmarthome.signIn.MainActivity
import com.google.android.material.snackbar.Snackbar


class OnOffDevicesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentOnOffDevicesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_on_off_devices, container, false
        )

        val arguments = OnOffDevicesFragmentArgs.fromBundle(requireArguments())
        println("Hello world"+arguments.deviceType)

        val application = requireNotNull(this.activity).application

        val arduinoId = MainActivity.globalVar

        val viewModelFactory = OnOffDevicesViewModelFactory(arduinoId,arguments.deviceType, resources , application)

        val onOffDevicesViewModel = ViewModelProvider(this, viewModelFactory).get(OnOffDevicesViewModel::class.java)


        binding.onOffDevicesViewModel = onOffDevicesViewModel

        binding.lifecycleOwner = this

        val recyclerViewList = binding.devicesList

        val adapterOnOffDevices = OnOffDevicesAdapter(
            OnDeviceClickListener {
                    device ->
                if(device!=null) {

                    onOffDevicesViewModel.turnOn(device.id,device.pin,device.type)
                }
            } , OffDeviceClickListener {
                    device ->
                if(device!=null) {

                    onOffDevicesViewModel.turnOff(device.id,device.pin,device.type)
                }
            })

        val adapterControlDevices = ControlDevicesAdapter(OnDeviceControlClickListener {
            device ->
            if(device!=null){

                onOffDevicesViewModel.turnOn(device.id,device.pin,device.type)
            }
        }, OffDeviceControlClickListener { 
                device -> if(device!=null){

            onOffDevicesViewModel.turnOff(device.id,device.pin,device.type)  
                } },
        UpDeviceControlClickListener { 
            device ->
            if(device != null){
                onOffDevicesViewModel.upDevice(device.id, device.pin, device.type)
            }
        },
        DownDeviceControlClickListener {
            device ->
            if(device != null){
                onOffDevicesViewModel.downDevice(device.id, device.pin, device.type)
            }
        })

        if(arguments.deviceType == 1 || arguments.deviceType == 4 || arguments.deviceType == 5 )
        {

             recyclerViewList.adapter = adapterOnOffDevices
        }
        else{
            recyclerViewList.adapter = adapterControlDevices
        }




        onOffDevicesViewModel.devicesList.observe(viewLifecycleOwner, Observer { devices ->

            devices?.let{
                adapterOnOffDevices.submitList(devices)
                adapterControlDevices.submitList(devices)
            }
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val device = adapterOnOffDevices.currentList[viewHolder.adapterPosition]
                var tableIs = ""
                when(device.type){
                    1 -> tableIs = "lamp"
                    2 -> tableIs = "ac"
                    3 -> tableIs = "tv"
                    4 -> tableIs = "lamp"
                    5 -> tableIs = "lamp"
                }
                onOffDevicesViewModel.delete(tableIs,device.id,device.type)
                Snackbar.make(requireView(),"Device deleted", Snackbar.LENGTH_LONG).show()
            }
        }).attachToRecyclerView(recyclerViewList)


        return binding.root
    }


}