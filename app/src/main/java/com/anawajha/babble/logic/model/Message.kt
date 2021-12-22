package com.anawajha.babble.logic.model

import org.json.JSONObject

data class Message(var source_id:String, var destination_id:String ,var message:Any){

    fun encode():JSONObject{
        var message= JSONObject()
        message.put("source_id",this.source_id)
        message.put(destination_id,this.destination_id)
        message.put("message",this.message)
        return message
    }

    fun decode(obj:JSONObject):Message{
        return Message(obj.getString("source_id"),obj.getString("destination_id").toString(),obj.get("message"))
    }

}