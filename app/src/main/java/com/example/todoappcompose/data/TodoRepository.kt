package com.example.todoappcompose.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TodoRepository @Inject constructor(db: FirebaseDatabase): ITodoRepository {

    private val databaseReference = db.getReference("TodoDatabase").child("todos")

    override suspend fun getTodoById(id: String): Todo? {
        return suspendCoroutine { continuation ->
            databaseReference.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val todo = snapshot.getValue(Todo::class.java)
                    continuation.resume(todo)
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resume(null)
                }
            })
        }
    }

    override suspend fun insertTodo(todo: Todo) {
        databaseReference.child(todo.id).setValue(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        databaseReference.child(todo.id).removeValue()
    }

    override fun getTodos(): Flow<List<Todo>> = callbackFlow {
        val eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val todos = snapshot.children.mapNotNull {
                    it.getValue(Todo::class.java)
                }

                trySend(todos).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        databaseReference.addValueEventListener(eventListener)

        awaitClose { databaseReference.removeEventListener(eventListener) }
    }
}

