package com.example.muhammad_idris.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.muhammad_idris.Model.DataItem
import com.example.muhammad_idris.Network.NetworkConfig
import com.example.muhammad_idris.R
import com.example.muhammad_idris.databinding.ListMahasiswaAdapterBinding


data class TekomAdapter(
    private val listPosts : List<DataItem>?,
    private val onListItemClick : (DataItem?)-> Unit
): RecyclerView.Adapter<TekomAdapter.ViewHolder>(){

    inner class ViewHolder(val ListPostsAdapterBinding : ListMahasiswaAdapterBinding) :
        RecyclerView.ViewHolder(ListPostsAdapterBinding.root) {
        fun onBindItem(dataItem2: DataItem?){
            Glide.with(ListPostsAdapterBinding.root.context)
                .load(NetworkConfig().GAMBAR_URL + dataItem2?.foto)
                .apply(RequestOptions().placeholder(R.drawable.baseline_person_24))
                .into(ListPostsAdapterBinding.gambar)
            ListPostsAdapterBinding.name.text = dataItem2?.namalengkap
            ListPostsAdapterBinding.datepost.text = dataItem2?.nim.toString()
            ListPostsAdapterBinding.namaP.text = dataItem2?.gender
            ListPostsAdapterBinding.root.setOnClickListener{
                onListItemClick(dataItem2)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
         ListMahasiswaAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(listPosts?.get(position))
    }

    override fun getItemCount(): Int {
        return listPosts?.size ?: 0

    }
}

