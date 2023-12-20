package it.unipi.dsmt.jakartaee.lab_07.serializers;

import com.google.gson.Gson;
import it.unipi.dsmt.jakartaee.lab_07.dto.MessageDTO;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public class MessageDTOEncoder implements Encoder.Text<MessageDTO>{
    private static Gson gson = new Gson();
    @Override
    public String encode(MessageDTO messageDTO) throws EncodeException {
        return gson.toJson(messageDTO);
    }
}
