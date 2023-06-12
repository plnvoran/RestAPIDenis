package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddingItemResponse {
    private String success;
    private String message;
    private String updatetopcartsectionhtml;
    private String updateflyoutcartsectionhtml;

}
