package com.example.demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.databinding.FragmentTestBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TestFragment : Fragment() {
    lateinit var binding: FragmentTestBinding
    val viewModel by activityViewModels<ActivityViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentTestBinding.inflate(inflater,container,false)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class, FlowPreview::class)
    @SuppressLint("RestrictedApi", "UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



                binding.btnClick.setOnClickListener {


                        viewModel.sendEvent(Event.NavigateToTestFragment2)




                }

        lifecycleScope.launch {

                viewModel.eventStateFlow
                    .collect { event ->
                        when (event) {
                            Event.Ideal -> {
                                Log.e("event here", "ideal")
                            }

                            Event.NavigateToTestFragment2 -> {
                                findNavController().navigate(R.id.test2Fragment)
                            }
                        }
                    }



        }




    }
    /*viewModel.eventChannel.consumeEach {
                        event->
                    Log.e("in event","${findNavController().currentBackStack.value.size}")
                    when(event){
                        Event.Ideal -> {
                            Log.e("event here","ideal")
                        }
                        Event.NavigateToTestFragment2 -> {
                            findNavController().navigate(R.id.test2Fragment)
                        }
                    }
                }*/
    override fun onDestroy() {
        super.onDestroy()
        Log.e("destroy","onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("destroy","onViewDestroy")
    }


}

fun NavController.safeNavigate(resId:Int){
    if(currentDestination?.id != resId){
        navigate(resId)
    }


}

