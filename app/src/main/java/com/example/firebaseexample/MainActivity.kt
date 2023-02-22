package com.example.firebaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.firebaseexample.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {

    val personRef = Firebase.firestore.collection("persons")

    val personsAdapter = PersonsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.apply {
            button.setOnClickListener{
                val name = etName.text.toString()
                val surname = etSurname.text.toString()
                val age = etAge.text.toString().toInt()
                val height = etHeight.text.toString().toDouble()

                val person = Person(
                    height = height,
                    name = name,
                    surname = surname,
                    age = age,
                )

                addPerson(person)
            }

            rvPersons.adapter = personsAdapter
            getPerson()
        }
    }

    private fun addPerson(person: Person){
        personRef.add(person)
    }

    private fun getPerson() = CoroutineScope(Dispatchers.Main).launch {
        val personsSnapshot = personRef.get().await()
        val personList = ArrayList<Person>()

        for (document in personsSnapshot.documents) {
            val person = document.toObject<Person>()
            if (person != null) {
                personList.add(person)
            }
        }
        personsAdapter.setData(personList)
        personsAdapter.notifyDataSetChanged()
    }
}