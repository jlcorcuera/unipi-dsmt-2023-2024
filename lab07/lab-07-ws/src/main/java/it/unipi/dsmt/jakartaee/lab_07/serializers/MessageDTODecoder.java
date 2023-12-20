package it.unipi.dsmt.jakartaee.lab_07.serializers;

import com.google.gson.Gson;
import it.unipi.dsmt.jakartaee.lab_07.dto.MessageDTO;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

public class MessageDTODecoder implements Decoder.Text<MessageDTO>{
    private static Gson gson = new Gson();
    @Override
    public MessageDTO decode(String s) throws DecodeException {
        return gson.fromJson(s, MessageDTO.class);
    }

    @Override
    public boolean willDecode(String s) {
        return s != null && !s.isEmpty();
    }
}
