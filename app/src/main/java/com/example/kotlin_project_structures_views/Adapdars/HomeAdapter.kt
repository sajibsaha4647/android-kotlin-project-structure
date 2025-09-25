package com.example.kotlin_project_structures_views.Adapdars

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_project_structure_new.databinding.ItemRowListviewHomeBinding
import com.example.kotlin_project_structures_views.Model.HomeResponseModel


class HomeAdapter(private var homeList: List<HomeResponseModel>,
                  private val listener: OnTaskActionListener ) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRowListviewHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val home = homeList[position]
        holder.binding.textViewId.text = home.id.toString()
        holder.binding.textViewText.text = home.name
        holder.binding.editButtonId.setOnClickListener {
            listener.onEditClick(position,home)
        }
        holder.binding.deleteButtonId.setOnClickListener {
            listener.onDeleteClick(position,home)
        }
    }

    override fun getItemCount(): Int {
        return  homeList.size ;
    }


    inner class ViewHolder(val binding: ItemRowListviewHomeBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnTaskActionListener{
        fun onEditClick(position: Int,homeList: HomeResponseModel)
        fun onDeleteClick(position: Int,homeList :HomeResponseModel)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<HomeResponseModel>) {
        homeList = newList
        notifyDataSetChanged() // simple but works
    }





}
