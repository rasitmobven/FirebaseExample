package com.example.firebaseexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseexample.databinding.ItemPersonBinding

class PersonsAdapter : RecyclerView.Adapter<PersonsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPersonBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    private var personList = ArrayList<Person>()

    fun setData(person: ArrayList<Person>){
        personList = person
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(personList[position])
    }

    inner class ViewHolder(val binding : ItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(person: Person){
            binding.person = person
        }
    }

}