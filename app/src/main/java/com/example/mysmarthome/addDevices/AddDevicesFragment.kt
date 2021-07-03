package com.example.mysmarthome.addDevices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mysmarthome.signIn.MainActivity.Companion.globalVar
import com.example.mysmarthome.R
import com.example.mysmarthome.databinding.FragmentAddDevicesBinding
import kotlin.properties.Delegates


class AddDevicesFragment : DialogFragment() {
    lateinit var deviceName:String
    lateinit var deviceType:String
    var pin by Delegates.notNull<Int>()
    lateinit var freePins:List<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentAddDevicesBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_add_devices,container,false)

        val application = requireNotNull(this.activity).application

        val arduinoId = globalVar

        val viewModelFactory = AddDevicesViewModelFactory(arduinoId,resources,application)

        val addDevicesViewModel = ViewModelProvider(this, viewModelFactory).get(AddDevicesViewModel::class.java)

        binding.addDevicesViewModel = addDevicesViewModel

        binding.lifecycleOwner = this

        val tableNames = resources.getStringArray(R.array.tableNames)

        val lampPins =  resources.getIntArray(R.array.lamp_pins)

        val restOfDevicesPins = arrayOf(0)


        fun updateInfo(){
            deviceName = binding.deviceNameTxt.text.toString()
        }

        val deviceSpinner = binding.spinnerDeviceType

        val pinsSpinner = binding.spinnerDevicePin




        ArrayAdapter.createFromResource(application,
        R.array.devices_Names,
        android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            deviceSpinner.adapter = adapter
        }

        addDevicesViewModel.pinsForDevice.observe(viewLifecycleOwner, Observer { availablePins->
            freePins = availablePins
            ArrayAdapter(application,android.R.layout.simple_spinner_item,availablePins).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                pinsSpinner.adapter = adapter
            }
        })


        deviceSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                if(tableNames[position] == "lamp" ){
                    addDevicesViewModel.getDevicePins(tableNames[position], arduinoId, lampPins.toList())
                    deviceType = tableNames[position]
                }
                else{
                    addDevicesViewModel.getDevicePins(tableNames[position], arduinoId, restOfDevicesPins.toList())
                    deviceType = tableNames[position]
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        pinsSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                pin = freePins[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.confirmAddBtn.setOnClickListener {
            updateInfo()
            addDevicesViewModel.addDevicesInTables(deviceType, deviceName,pin,arduinoId)

        }
        addDevicesViewModel.added.observe(viewLifecycleOwner, Observer {
            if(it == true){
                dismiss()
                addDevicesViewModel.onAddedComplete()
            }
        })

        return binding.root
    }


}