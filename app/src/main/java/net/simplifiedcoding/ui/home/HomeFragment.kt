package net.simplifiedcoding.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import net.simplifiedcoding.R
import net.simplifiedcoding.data.network.Resource
import net.simplifiedcoding.data.responses.User
import net.simplifiedcoding.databinding.FragmentHomeBinding
import net.simplifiedcoding.ui.handleApiError
import net.simplifiedcoding.ui.logout
import net.simplifiedcoding.ui.qr.ShowQR
import net.simplifiedcoding.ui.qr.QrCodeScanner
import net.simplifiedcoding.ui.visible
import okhttp3.OkHttpClient
import okhttp3.Request


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home)   {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.progressbar.visible(false)
        setHasOptionsMenu(true);


        //Toolbar Menu Logic
        binding.myToolbar.inflateMenu(R.menu.menu_example)

        binding.myToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.showqr -> {
                    // Navigate to settings screen
                    val intent = Intent(activity, ShowQR::class.java)

                    startActivity(intent)
                    true
                }
                R.id.scanqr -> {
                    // Save profile changes
                    val intent = Intent(activity, QrCodeScanner::class.java)
                    startActivity(intent)
                    true
                }
                R.id.logout -> {
                    // Save profile changes
                    logout()
                    true
                }
                else -> false
            }
        }

        viewModel.getUser()

        viewModel.user.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressbar.visible(false)
                    updateUI(it.value.user)
                }
                is Resource.Loading -> {
                    binding.progressbar.visible(true)
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
            }
        })


        //Convertir Moneda
        binding.buttonConvertir.setOnClickListener {


            //API Peticion



            Toast.makeText(this.requireContext(), "", Toast.LENGTH_SHORT).show()
        }




    }





    private fun updateUI(user: User) {
        with(binding) {
            textViewId.text = user.id.toString()
            textViewName.text = user.name
            textViewEmail.text = user.email
        }
    }







}