/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentStartBinding
import com.example.cupcake.model.OrderViewModel

/**
 * This is the first screen of the Cupcake app. The user can choose how many cupcakes to order.
 */
class StartFragment : Fragment() {

    private val sharedViewModel: OrderViewModel by activityViewModels()
    // viewModels()는 현재 프래그먼트로 범위가 지정된 ViewModel 인스턴스를 제공합니다. 따라서 인스턴스는 프래그먼트마다 다릅니다.
    // activityViewModels()는 현재 활동으로 범위가 지정된 ViewModel 인스턴스를 제공합니다.
    // 따라서 인스턴스는 동일한 활동의 여러 프래그먼트 간에 동일하게 유지됩니다.

    // Binding object instance corresponding to the fragment_start.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentStartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding?.apply {
//            // Set up the button click listeners
//            orderOneCupcake.setOnClickListener { orderCupcake(1) }
//            orderSixCupcakes.setOnClickListener { orderCupcake(6) }
//            orderTwelveCupcakes.setOnClickListener { orderCupcake(12) }
//        }

        binding?.startFragment = this
    }

    /**
     * Start an order with the desired quantity of cupcakes and navigate to the next screen.
     */
    fun orderCupcake(quantity: Int) {
//        Toast.makeText(activity, "Ordered $quantity cupcake(s)", Toast.LENGTH_SHORT).show()
        sharedViewModel.setQuantity(quantity)
        if (sharedViewModel.hasNoFlavorSet()) {
            sharedViewModel.setFlavor(getString(R.string.vanilla)) // 기본 맛
        }
        findNavController().navigate(R.id.action_startFragment_to_flavorFragment)
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}