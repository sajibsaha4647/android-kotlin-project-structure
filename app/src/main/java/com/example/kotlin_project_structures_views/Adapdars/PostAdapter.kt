package com.example.kotlin_project_structures_views.Adapdars

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_project_structure_new.databinding.ItemPostListviewAboutBinding
import com.example.kotlin_project_structures_views.Model.Post

class PostAdapter(private var postList: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
       val binding = ItemPostListviewAboutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  PostViewHolder(binding) ;
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {
        val post = postList[position]
        holder.binding.textViewId.text = post.title
        holder.binding.textViewText.text = post.body
    }

    override fun getItemCount(): Int {
        return  postList.size ;
    }

   inner class PostViewHolder(val binding: ItemPostListviewAboutBinding) : RecyclerView.ViewHolder(binding.root)


    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<Post>) {
        postList = newList
        notifyDataSetChanged() // simple but works
    }

}