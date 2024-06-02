package com.example;

import com.example.BitCaskHandler.BitCask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/bitcask")
public class BitCaskController {

    private final BitCask bitCask;

    @Autowired
    public BitCaskController(BitCask bitCask) {
        this.bitCask = bitCask;
    }

    @PostMapping("/write")
    public String writeRecord(@RequestBody Map<String, Object> payload) {
        try {
            int key = (Integer) payload.get("key");
            String value = (String) payload.get("value");
            bitCask.writeRecordToActiveFile(key, value);
            return "Record written successfully";
        } catch (Exception e) {
            return "Error writing record: " + e.getMessage();
        }
    }

    @GetMapping("/read/{key}")
    public String readRecord(@PathVariable int key) {
        try {
            return bitCask.readRecordForKey(key);
        } catch (IOException e) {
            return "Error reading record: " + e.getMessage();
        }
    }
}
