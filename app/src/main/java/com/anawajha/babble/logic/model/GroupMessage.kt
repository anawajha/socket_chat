package com.anawajha.babble.logic.model

import com.anawajha.babble.shared.Constants
import org.json.JSONObject

data class GroupMessage(var source_id:String, var source_name:String, var message:String?, var imageMessage:ByteArray?) {

    fun encode():JSONObject{
        var message = JSONObject()
        message.put(Constants.SOURCE_ID,this.source_id)
        message.put(Constants.SOURCE_NAME,this.source_name)
        message.put(Constants.MESSAGE,this.message)
        message.put(Constants.IMAGE_MESSAGE,this.imageMessage)
        return message
    }// encode

    companion object{
        fun decode(obj:JSONObject):GroupMessage{
            return GroupMessage(obj.getString(Constants.SOURCE_ID), obj.getString(Constants.SOURCE_NAME),
                obj.getString(Constants.MESSAGE),obj.get(Constants.IMAGE_MESSAGE) as ByteArray)
        }// decode

    }// companion object

}// GroupMessage