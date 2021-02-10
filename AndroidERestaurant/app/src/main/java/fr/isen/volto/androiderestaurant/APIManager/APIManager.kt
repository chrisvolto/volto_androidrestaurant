package fr.isen.volto.androiderestaurant.APIManager

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import fr.isen.volto.androiderestaurant.Category
import fr.isen.volto.androiderestaurant.ProductOrder
import fr.isen.volto.androiderestaurant.User
import org.json.JSONObject


class APIManager(private val mReqQueue: RequestQueue, val resListener: (data: Any?) -> Any) {

    fun makeOrder(user: User, orderJSON: String){
        sendPostRequest(
                "user/order",
                hashMapOf(
                        "id_shop" to "1",
                        "id_user" to user.id.toString(),
                        "msg" to orderJSON
                ),
                User::class.java //TODO Quel retour de l'API ?
        )
    }

    fun login(email: String, password: String){
        sendPostRequest(
            "user/login",
            hashMapOf(
                "id_shop" to "1",
                "email" to email,
                "password" to password
            ),
            User::class.java
        )
    }

    fun register(firstName: String, lastName: String, address: String, email: String, password: String){
        sendPostRequest(
                "user/register",
                hashMapOf(
                        "id_shop" to "1",
                        "firstname" to firstName,
                        "lastname" to lastName,
                        "address" to address,
                        "email" to email,
                        "password" to password
                ),
                User::class.java
        )
    }


    fun getAllCategories() {
        sendPostRequest(
            "menu",
            hashMapOf(
                "id_shop" to "1"
            ),
            Array<Category>::class.java
        )
    }

    fun <T: Any> sendPostRequest(reqPath: String, reqParams: HashMap<String, String>, resClass: Class<T>){

        var mStringRequest = object : StringRequest(
            Request.Method.POST,
            "http://test.api.catering.bluecodegames.com/$reqPath",
            Response.Listener
            {
                response ->
                    try{
                        Log.i("test","1")
                        JSONObject(response).get("data").let {
                            val ret = Gson().fromJson(it.toString(), resClass)

                            resListener(ret)
                        }
                    }catch (e: Exception) {
                        try{
                            resListener(JSONObject(response).get("msg").toString())
                        }catch (e2: Exception){
                            resListener(null)
                        }
                    }
            },
            Response.ErrorListener
            {
                    _ -> resListener(null)
            }
        ) {

            override fun getBodyContentType(): String {
                return "application/json"
            }

            override fun getBody(): ByteArray {
                return JSONObject(reqParams as Map<*, *>).toString().toByteArray()
            }

        }
        mReqQueue.add(mStringRequest)
    }

}