package com.example.mysmarthome.devicesScreen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mysmarthome.MainActivity
import com.example.mysmarthome.MainActivity.Companion.globalVar
import com.example.mysmarthome.R
import com.example.mysmarthome.addDevices.AddDevicesFragment
import com.example.mysmarthome.databinding.FragmentDevicesScreenBinding


class DevicesScreenFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding:FragmentDevicesScreenBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_devices_screen, container , false
        )

        val application = requireNotNull(this.activity).application

        val arduinoId = globalVar

        val viewModelFactory = DevicesScreenViewModelFactory(arduinoId,resources, application)

        val devicesScreenViewModel = ViewModelProvider(this, viewModelFactory).get(DevicesScreenViewModel::class.java)

        binding.devicesScreenViewModel = devicesScreenViewModel

        binding.lifecycleOwner = this

        var deviceTypeId = 0

        val adapter = DevicesAdapter(DeviceClickListener {
            device -> devicesScreenViewModel.onDeviceClicked(device.deviceId)
            deviceTypeId = device.deviceId
        })

        val recyclerViewList = binding.recyclerView

        recyclerViewList.adapter = adapter

        devicesScreenViewModel.devices.observe(viewLifecycleOwner, Observer { devices ->
        devices?.let{
            adapter.submitList(devices)
        }
        })

        devicesScreenViewModel.eventNavigate.observe(viewLifecycleOwner, Observer {
            if(it == true){
                this.findNavController().navigate(DevicesScreenFragmentDirections.actionDevicesScreenFragmentToOnOffDevicesFragment(deviceTypeId))
                devicesScreenViewModel.onNavigationCompleted()
            }
        })

        binding.addDeviceBtn.setOnClickListener {
            //this.findNavController().navigate(DevicesScreenFragmentDirections.actionDevicesScreenFragmentToAddDevicesFragment2())
            val fragmentManager = this.parentFragmentManager
            var dialog = AddDevicesFragment()

            dialog.show(fragmentManager,"Hello")

        }

        binding.imageView3.setOnClickListener{
            devicesScreenViewModel.getDevices(arduinoId)
        }



        return binding.root
    }

    override fun onPause() {
        super.onPause()
        Log.d("Heloooooooo", "onPause: Fragment Paused")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Heloooooooo", "onPause: Fragment Resumed")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Heloooooooo", "onPause: Fragment Created")
    }
}