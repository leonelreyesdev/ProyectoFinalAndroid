package com.example.proyectofinalandroid

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvHaveAccount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvHaveAccount = findViewById(R.id.tvHaveAccount)

        btnRegister.setOnClickListener {
            attemptRegister()
        }

        tvHaveAccount.setOnClickListener {
            Toast.makeText(this, "Aquí podrías abrir la pantalla de login", Toast.LENGTH_SHORT).show()
        }
    }

    private fun attemptRegister() {
        val username = etUsername.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        // Validaciones básicas
        if (username.isEmpty()) {
            etUsername.error = "Ingresa un nombre de usuario"
            etUsername.requestFocus()
            return
        }

        if (!isValidEmail(email)) {
            etEmail.error = "Ingresa un correo válido"
            etEmail.requestFocus()
            return
        }

        if (password.length < 6) {
            etPassword.error = "La contraseña debe tener al menos 6 caracteres"
            etPassword.requestFocus()
            return
        }

        // Guardar usuario en SharedPreferences (ejemplo sencillo)
        val prefs = getSharedPreferences("users_prefs", Context.MODE_PRIVATE)
        val usersJson = prefs.getString("users", "[]") ?: "[]"
        val usersArray = JSONArray(usersJson)

        // Verificar si el correo ya está registrado
        for (i in 0 until usersArray.length()) {
            val userObj = usersArray.getJSONObject(i)
            if (userObj.optString("email").equals(email, ignoreCase = true)) {
                Toast.makeText(this, "Este correo ya está registrado", Toast.LENGTH_LONG).show()
                return
            }
        }

        // Crear objeto usuario
        val newUser = JSONObject()
        newUser.put("username", username)
        newUser.put("email", email)
        newUser.put("password", password) // NO almacenar contraseñas en texto plano en producción

        usersArray.put(newUser)

        // Guardar de vuelta
        prefs.edit().putString("users", usersArray.toString()).apply()

        Toast.makeText(this, "¡Registro exitoso!", Toast.LENGTH_LONG).show()

        // Limpiar campos
        etUsername.text.clear()
        etEmail.text.clear()
        etPassword.text.clear()
    }

    private fun isValidEmail(email: String): Boolean {
        // Validación simple con patrón de Android
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}