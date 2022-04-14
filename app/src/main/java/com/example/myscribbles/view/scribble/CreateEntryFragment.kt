package com.example.myscribbles.view.scribble

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myscribbles.R
import com.example.myscribbles.model.Entry
import com.example.myscribbles.utils.TimeUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_entry_layout.*
import java.util.*

@AndroidEntryPoint
class CreateEntryFragment : Fragment() {
    val createEntryViewModel: CreateEntryViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val getSelectedImages =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            createEntryViewModel.updateImageList(it)
        }

    @SuppressLint("MissingPermission")
    val locationPermissionRequestResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGiven ->
            if (isGiven) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.let {
                            createEntryViewModel.updateLocation(location)
                        }

                    }
            } else {

            }
        }

    val storagePermissionRequestResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGiven ->
            if (isGiven) {
                getSelectedImages.launch("image/*")
            } else {
                //TODO(show dialog)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.create_entry_layout, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createEntryViewModel.updateDate(Date(System.currentTimeMillis()))

        createEntryViewModel.onDateSelected().observe(viewLifecycleOwner) {
            create_entry_year_date.text = TimeUtils.formatYearDay(it)
            create_entry_date_month.text = TimeUtils.formatDateMonth(it)
            create_entry_time.text = TimeUtils.formatTime(it)
        }




        create_entry_date_picker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                createEntryViewModel.updateDate(Date(it))
            }

            datePicker.show(this.parentFragmentManager, "Select date")
        }

        create_entry_time_picker.setOnClickListener {
            val timePicker =
                MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
            timePicker.addOnPositiveButtonClickListener {
                val hour: Int = timePicker.hour
                val minute: Int = timePicker.minute
                createEntryViewModel.updateDate(hour, minute)
            }
            timePicker.show(this.parentFragmentManager, "Select time")
        }

        create_entry_save_button.setOnClickListener {
            if (!create_entry_title.text.isEmpty() && !create_entry_content.text.isEmpty()) {
                val newEntry = Entry(
                    0,
                    create_entry_title.text.toString().trim(),
                    create_entry_content.text.toString().trim(),
                    createEntryViewModel.getSelectedTime()
                )
                createEntryViewModel.insertNewEntry(newEntry)
                val action =
                    CreateEntryFragmentDirections.actionCreateEntryFragmentNavToScribbleFragmentNav(
                        true
                    )
                findNavController().navigate(action)
            }
        }

        create_entry_location_button.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            location?.let {
                                location?.let {
                                    createEntryViewModel.updateLocation(location)
                                }
                            }
                        }
                }
                else -> {
                    locationPermissionRequestResult.launch(ACCESS_COARSE_LOCATION)
                }
            }
        }

        create_entry_add_image_button.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    getSelectedImages.launch("image/*")
                }
                else -> {
                    storagePermissionRequestResult.launch(READ_EXTERNAL_STORAGE)
                }
            }
        }
    }

    //for stopping to resize keyboard
    private var originalMode: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        originalMode?.let { activity?.window?.setSoftInputMode(it) }
    }
}