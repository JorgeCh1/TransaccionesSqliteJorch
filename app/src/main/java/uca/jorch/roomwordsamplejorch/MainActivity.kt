package uca.jorch.roomwordsamplejorch

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uca.jorch.roomwordsamplejorch.adapter.NombreListAdapter

class MainActivity : AppCompatActivity() {

    private val newNombreActivityRequestCode = 1
    private val NombreViewModel: NombreViewModel by viewModels {
        NombreViewModelFactory((application as NombreAplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NombreListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewNombreActivity::class.java)
            startActivityForResult(intent, newNombreActivityRequestCode)
        }


        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        NombreViewModel.allWords.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.submitList(it) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newNombreActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(NewNombreActivity.EXTRA_REPLY)?.let { reply ->
                val nombre = Nombre(reply)
                NombreViewModel.insert(nombre)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}