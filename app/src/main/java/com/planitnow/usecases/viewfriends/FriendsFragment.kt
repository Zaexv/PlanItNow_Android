package com.planitnow.usecases.viewfriends

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.planitnow.R
import com.planitnow.databinding.FragmentFriendsBinding
import com.planitnow.usecases.addfriend.AddFriendFragment
import com.planitnow.usecases.mainactivity.MainActivity
import com.planitnow.usecases.notifications.NotificationsRouter

class FriendsFragment : Fragment() {

    private lateinit var friendsViewModel: FriendsViewModel
    private var _binding: FragmentFriendsBinding? = null
    private lateinit var friendsAdapter: FriendsAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        friendsViewModel =
            ViewModelProvider(this).get(FriendsViewModel::class.java)
        initializeToolbar()


        friendsAdapter = FriendsAdapter(friendsViewModel)

        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.listFriendsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.listFriendsRecyclerView.adapter = friendsAdapter

        return root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            friendsViewModel.refreshMyFriends()
            friendsAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeToolbar(){
        toolbar = (activity as AppCompatActivity).findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.menu.clear()
        toolbar.findViewById<ImageView>(R.id.app_logo).visibility = View.VISIBLE
        toolbar.inflateMenu(R.menu.friends_menu)
        toolbar.setOnMenuItemClickListener{ item ->
            when (item.itemId){
                R.id.action_add_friend -> showAddFriendDialog()
                R.id.action_notifications -> NotificationsRouter().launch(requireContext())
                else -> Toast.makeText(toolbar.context,"purzao",Toast.LENGTH_SHORT).show()
            }
            false
        }

    }

    private fun showAddFriendDialog(){
        var addFriendFragment : AddFriendFragment = AddFriendFragment.instance
        if (!addFriendFragment.isAdded) addFriendFragment.show(
            requireActivity().supportFragmentManager,"add_friend")
    }


}