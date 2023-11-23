package com.example.youthconnect.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youthconnect.Model.Users.Child
import com.example.youthconnect.Model.Users.Instructor
import com.example.youthconnect.Model.Users.Parent
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InstructorViewModel : ViewModel(){
    private val firestore = FirebaseFirestore.getInstance()
    private val _instructorState = MutableStateFlow<List<Instructor>>(emptyList())
    val instructorState: Flow<List<Instructor>> = _instructorState.asStateFlow()


    fun getCurrentUserById(instructorId : String ){

        viewModelScope.launch {
            firestore.collection("Instructor")
                .whereEqualTo("id", instructorId)
                .get()
                .addOnSuccessListener { documents ->
                    var foundInstructor: Instructor? = null
                    for (document in documents) {
                        val instructor = Instructor(
                            FullName = document.getString("FullName") ?: "",
                            ID = document.getString("ID") ?: "",
                            Password = document.getString("Password") ?: ""

                        )

                        foundInstructor = instructor
                        break // Termina el bucle después de encontrar la primera noticia
                    }

                    if (foundInstructor != null) {

                        _instructorState.value = listOf(foundInstructor!!)
                    } else {
                        // No se encontró ninguna noticia con esa ID
                        // Puedes manejar el caso estableciendo el estado con un valor nulo o un indicador de ausencia
                        _instructorState.value = emptyList() // Por ejemplo, aquí se establece una lista vacía
                    }
                }
                .addOnFailureListener { exception ->
                    // Manejar errores aquí
                }
        }

    }


    fun buscarChildsPorInstructor(instructorID: String) {
        viewModelScope.launch {
            try {
                val childs = obtenerChildsPorInstructorID(instructorID)
                childsPorInstructor.value = childs
            } catch (e: Exception) {
                // Manejar cualquier error que pueda ocurrir al buscar los Childs por InstructorID
                e.printStackTrace()
            }
        }
    }

    private suspend fun obtenerChildsPorInstructorID(instructorID: String): List<Child> {
        return withContext(Dispatchers.IO) {
            try {
                val querySnapshot = db.collection("Child")
                    .whereEqualTo("InstructorID", instructorID)
                    .get()
                    .await()

                val childsList: MutableList<Child> = mutableListOf()
                for (document in querySnapshot.documents) {
                    val child = document.toObject(Child::class.java)
                    child?.let {
                        childsList.add(it)
                    }
                }

                return@withContext childsList
            } catch (e: Exception) {
                // Manejar cualquier error que pueda ocurrir al buscar los Childs por InstructorID
                e.printStackTrace()
                return@withContext emptyList()
            }
        }
    }
}


}
