package com.anawajha.babble.logic.model

import org.json.JSONObject

data class User(var id:String, var name:String, var email:String,var image:String){

    fun encode(): JSONObject {
        var user = JSONObject()
        user.put("id",this.id)
        user.put("name",this.name)
        user.put("email",this.email)
        user.put("image",this.image)
        return user
    }// encode

 companion object{
     fun decode(obj: JSONObject):User{
         return User(obj.getString("id"),obj.getString("name").toString(),obj.getString("email"),obj.getString("image"))
     }// decode
 }// companion object
}// User class