package com.example.mysmarthome.devicesScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mysmarthome.MainActivity.Companion.globalVar
import com.example.mysmarthome.R
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

        val adapter = DevicesAdapter(DeviceClickListener {
            device -> devicesScreenViewModel.onDeviceClicked(device)
        })

        val recyclerViewList = binding.recyclerView

        recyclerViewList.adapter = adapter

        devicesScreenViewModel.devices.observe(viewLifecycleOwner, Observer { devices ->
        devices?.let{
            adapter.submitList(devices)
        }
        })



        return binding.root
    }


}