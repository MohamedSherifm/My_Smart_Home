package com.example.mysmarthome.onOffDevices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mysmarthome.MainActivity
import com.example.mysmarthome.OnOffDevicesAdapter
import com.example.mysmarthome.R
import com.example.mysmarthome.databinding.FragmentOnOffDevicesBinding


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

        val application = requireNotNull(this.activity).application

        val arduinoId = MainActivity.globalVar

        val viewModelFactory = OnOffDevicesViewModelFactory(arduinoId,arguments.deviceType, resources , application)

        val onOffDevicesViewModel = ViewModelProvider(this, viewModelFactory).get(OnOffDevicesViewModel::class.java)

        binding.onOffDevicesViewModel = onOffDevicesViewModel

        binding.lifecycleOwner = this

        val adapter = OnOffDevicesAdapter()

        val recyclerViewList = binding.devicesList

        recyclerViewList.adapter = adapter

        onOffDevicesViewModel.devicesList.observe(viewLifecycleOwner, Observer { devices ->
            devices?.let{
                adapter.submitList(devices)
            }
        })


        return binding.root
    }


}