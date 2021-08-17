package com.planitnow.usecases.addfriend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.planitnow.R
import com.planitnow.databinding.AddFriendLayoutBinding
import com.planitnow.databinding.FragmentFriendsBinding
import com.planitnow.databinding.FragmentHomeBinding
import com.planitnow.usecases.viewfriends.FriendsViewModel


class AddFriendFragment : BottomSheetDialogFragment() {

    private lateinit var addFriendViewModel: AddFriendViewModel
    private var _binding: AddFriendLayoutBinding? = null
    private val binding get() = _binding!!

    companion object {
        val instance = AddFriendFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addFriendViewModel =
            ViewModelProvider(this).get(AddFriendViewModel::class.java)
        inflater.inflate(R.layout.add_friend_layout, container, false)
        _binding = AddFriendLayoutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.addFriendCancel.setOnClickListener() {
            binding.addFriendUsername.text.clear()
            dismiss()
        }

        binding.addFriendButton.setOnClickListener() {
            lifecycleScope.launchWhenResumed {
                val toUsername = binding.addFriendUsername.text.toString()
                addFriendViewModel.sendFriendRequest(requireContext(), toUsername)
                binding.addFriendUsername.text.clear()
                dismiss()
            }

        }

        super.onViewCreated(view, savedInstanceState)
    }


}